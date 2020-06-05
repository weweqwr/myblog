package com.longer.shiro.relam;

import com.longer.entities.User;
import com.longer.mapperDao.UserDao;
import com.longer.service.PermissionService;
import com.longer.service.RoleService;
import com.longer.shiro.rewrite.UsernamePasswordTokenModel;
import com.longer.utils.ActiveUser;
import org.apache.shiro.authc.*;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *shiro的UserRelam
 * @author longer
 * @date 2020/4/19
*/
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Resource(name = "redisRedis1Template")
    private RedisTemplate redisRedis1Template;
    @Resource(name = "redisRedis2Template")
    private RedisTemplate redisRedis2Template;
    /**
     * 处理登录
    */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        ValueOperations<String,Object> operations=redisRedis1Template.opsForValue();
        UsernamePasswordTokenModel tokenModel= (UsernamePasswordTokenModel) token;
        String account=tokenModel.getPrincipal().toString();//账号
        Object credenticals=tokenModel.getCredentials();//密码
        int roleid=tokenModel.getRoleid();
        //根据用户名查询用户是否存在
        ActiveUser activeUsers=new ActiveUser();
        //从redis根据账号获取信息
        if(roleid==1) {
            activeUsers = (ActiveUser) redisRedis2Template.opsForValue().get(account+"User");//角色为1，获取用户缓存
        }else if(roleid==2){
            activeUsers = (ActiveUser) redisRedis2Template.opsForValue().get(account+"Admin");//角色为2，获取管理员
        }else{
            activeUsers = (ActiveUser) redisRedis2Template.opsForValue().get(account+"SuperAdmin");//角色为1，获取超级管理员缓存
        }
        User user=new User();
        if(activeUsers==null){ //如果账号信息为null的时候，再从数据库取数据，减轻数据库压力
            user = userDao.selectUserByAccount(account,roleid,1);
        }else{
            user=activeUsers.getUser();
        }
        if(user!=null){
            //根据账号判断用户有哪些角色
            List<String> roles=new ArrayList<>();
            if(activeUsers==null){//如果账号信息为null的时候，再从数据库取数据，减轻数据库压力
                roles = roleService.selectRoleByAccount(account);
            }else {
                roles=activeUsers.getRoles();
            }
            //根据账号查询用户有哪些权限
            List<String> permissions=new ArrayList<>();
            if(activeUsers==null) {//如果账号信息为null的时候，再从数据库取数据，减轻数据库压力
                permissions = permissionService.selectPermissionByAccount(account);
            }else{
                permissions=activeUsers.getPermissions();
            }
            //动态类缓存用户信息
            ActiveUser activeUser=new ActiveUser(user,roles,permissions);
            //将信息存入对应角色redis中
            if(roleid==1) {
                operations.set(account+"User", activeUser, 30, TimeUnit.DAYS);
            }else if(roleid==2){
                operations.set(account+"Admin", activeUser, 30, TimeUnit.DAYS);
            }else {
                operations.set(account+"SuperAdmin", activeUser, 30, TimeUnit.DAYS);
            }
            //用户名作为md5的盐
            ByteSource credentialsSalt=ByteSource.Util.bytes(user.getAccount());

            SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(activeUser,user.getPassword(),credentialsSalt,this.getName());

            System.out.println(info);
            return info;
        }
        return null;
    }

    /**
     * 处理权限
     * */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //根据用户名id查询有哪些角色
        List<String> roles = activeUser.getRoles();
        //如果角色不为空添加角色
        if (null != roles && roles.size() > 0) {
            info.addRoles(roles);
        }
        //根据id查询有哪些权限
        List<String> permissions = activeUser.getPermissions();
        if (null != permissions && permissions.size() > 0) {
            //添加权限
            info.addStringPermissions(permissions);
        }
        return info;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token!=null&&(token instanceof UsernamePasswordTokenModel ||token instanceof UsernamePasswordToken);
    }
}
