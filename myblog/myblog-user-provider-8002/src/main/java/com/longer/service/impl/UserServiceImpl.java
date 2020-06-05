package com.longer.service.impl;

import com.longer.entities.OBSEntity;
import com.longer.entities.SessionCons;
import com.longer.entities.User;
import com.longer.mapperDao.UserDao;

import com.longer.service.UserService;
import com.longer.shiro.rewrite.UsernamePasswordTokenModel;
import com.longer.utils.ActiveUser;
import com.longer.utils.Base64Util;
import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Resource(name = "redisRedis1Template")
    private RedisTemplate redisRedis1Template;
    @Resource(name = "redisRedis2Template")
    private RedisTemplate redisRedis2Template;

    /**
     * 用户注册
     *
     * @param account  String 账号
     * @param password String 密码
     * @return map Map<String,Object> 返回注册信息
     */
    @Override
    public Map<String, Object> insertUserRedist(String account, String password) {
        Map<String, Object> map = new HashMap<>();
        //查询是否有该账号
        User user = userDao.selectUserByAccount(account, 1, 1);
        //判断是否已有以被禁用用户
        if (user == null) {
            user = userDao.selectUserByAccount(account, 1, 0);
        }
        //判断是否有该账号，有则注册，没有返回已有该账号信息
        if (user != null) {
            Md5Hash md5 = new Md5Hash(password, account, 2);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String creteTime = format.format(new Date());
            userDao.insertUserRedist(account, md5.toString(), creteTime);
            //返回1，表示注册成功
            map.put("flag", 1);
            map.put("result", "注册成功");
        } else {
            //返回2，表示已有该账号
            map.put("flag", 2);
            map.put("result", "已有该账号");
        }

        return map;
    }

    /**
     * 根据账号查询用户信息
     *
     * @param account 账号
     * @param roleid  int
     * @param state   int
     * @author longer
     * @date 2020/4/21
     */
    @Override
    public Map<String, Object> selectUserByAccount(String account, int roleid, int state) {
        HashMap map = new HashMap();
        ActiveUser activeUser = new ActiveUser();
        //根据角色取信息
        if (roleid == 1) {
            activeUser = (ActiveUser) redisRedis2Template.opsForValue().get(account + "User");
        } else if (roleid == 2) {
            activeUser = (ActiveUser) redisRedis2Template.opsForValue().get(account + "Admin");
        } else if (roleid == 3) {
            activeUser = (ActiveUser) redisRedis2Template.opsForValue().get(account + "SuperAdmin");
        }
        User user = new User();
        if (activeUser == null) {
            user = userDao.selectUserByAccount(account, roleid, state);
        } else {
            user = activeUser.getUser();
        }
        map.put("result", user);
        map.put("flag", 1);
        return map;
    }

    /**
     * 用户登录
     *
     * @param account String
     * @author longer
     * @date 2020/4/19
     */
    @Override
    public Map<String, Object> doLogin(String account, String password, int roleid, int state,
                                       HttpServletResponse response,
                                       HttpServletRequest request,
                                       HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        //1、得到主体
        Subject subject = SecurityUtils.getSubject();
        //2、封装用户名和密码
        UsernamePasswordTokenModel token = new UsernamePasswordTokenModel(account, password, roleid);
        try {
            subject.login(token);
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            session.setAttribute("ActiveUser", activeUser);
            //存储sessionid
            String sessionId = SessionCons.TOKEN_PREFIX + UUID.randomUUID().toString();
            Cookie cookie = new Cookie("sessionId", sessionId);
            cookie.setPath("/");   //
            cookie.setMaxAge(60 * 60);       //一小时
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.addCookie(cookie);
//            存储rooleid 方便根据roleid查询判断是否根据那个sessionid登录
            Cookie cookierole = new Cookie("roleid", String.valueOf(roleid));
            cookierole.setPath("/");   //
            cookierole.setMaxAge(60 * 60);       //一小时
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.addCookie(cookierole);
            map.put("sessionId",sessionId);
            map.put("flag", 1);
            map.put("result", activeUser.getUser());
        } catch (AuthenticationException e) {
            map.put("flag", 2);
            map.put("result", "密码，账号不正确");
        }
        return map;
    }

    /**
     * 修改个人信息
     * 参数太多，不做累赘
     *
     * @author longer
     * @date 2020/4/23
     */
    @Override
    public boolean updateUserInfo(String avatarUrl, String nickname, String name,
                                  int gender, String province, String city, String district,
                                  String maxim, String introduct, String account) {
        boolean flag;
        try {
            String imageUrl = null;
            if (avatarUrl != null) { //判断是否需要修改图片，修改上传对象云存储
                OBSEntity obsEntity = new OBSEntity();
                String endPoint = obsEntity.getEndPoint();
                String ak = obsEntity.getAk();
                String sk = obsEntity.getSk();
                ObsClient obsClient = new ObsClient(ak, sk, endPoint);
                String ObsPath = "myBlog/adminAvatarUrl/" + account + "/head" + "/";
                //base64不能出现 data:image/png;base64,要去掉以下步骤去除
                String[] s = avatarUrl.split(";");
                String[] ss = avatarUrl.split(",");
                Base64Util bs = new Base64Util();
                //将base64转为file
                File file = bs.base64ToFile(ss[1], account + "jpg");
                //生成时间戳,作为文件夹区分头像文件
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmSSss");
                String creteTime = format.format(new Date());
                PutObjectResult result = obsClient.putObject("longlongago", ObsPath + creteTime + ".jpg", file);
                //获取云存储对象路径
                imageUrl = result.getObjectUrl();
                obsClient.close();
                //删除redis缓存防止redis数据不更新
                redisRedis1Template.delete(account + "User");
            }
            flag = userDao.updateUserInfo(imageUrl, nickname, name, gender, province, city, district, maxim, introduct, account);

        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 最近访客缓存进redis
     *
     * @param articleid int
     * @param account   账号
     * @author longer
     * @date 2020/5/2
     */
    @Override
    public void cashRecentVisitors(int articleid, String account) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmSSss");
        double score = Double.parseDouble(format.format(new Date()));
        User user = userDao.selectUserByAccount(account, 1, 1);
        String k = articleid + "Visitors";
        //先判断值是否大于12了
        long flag = redisRedis2Template.opsForZSet().zCard(k);
        if (flag == 12) {//等于12的时候删除最后一个
            redisRedis1Template.opsForZSet().removeRange(k, 12, 12);
        }
        redisRedis1Template.opsForZSet().add(k, user, score);
    }

    /**
     * 查询最新访客redis
     *
     * @param articleid int
     * @param account   账号
     * @author longer
     * @date 2020/5/2
     */
    @Override
    public Map<String, Object> queryRecentVisitors(int articleid, String account) {
        Map<String, Object> map = new HashMap<>();
        String k = articleid + "Visitors";
        Set result = redisRedis2Template.opsForZSet().range(k, 0, 11);
        map.put("result", result);
        map.put("flag", 1);
        return map;
    }

    /**
     * 管理员查询用户列表
     *
     * @param roleid    roleid
     * @param time      time
     * @param state     state
     * @param curPage   curPage
     * @param showCount showCount
     * @author longer
     * @date 2020/5/12
     */
    @Override
    public Map<String, Object> selectAdminUserBy(int roleid, int time, int state, int curPage, int showCount, String account) {
        Map<String, Object> map = new HashMap<>();
        List<User> user = userDao.selectAdminUserBy(roleid, time, state, curPage, showCount, account);
        map.put("result", user);
        map.put("flag", 1);
        return map;
    }

    /**
     * 管理员用户账号管理
     *
     * @param state int
     * @param state int
     * @author longer
     * @date 2020/5/12
     */
    @Override
    public Map<String, Object> updateUserStateById(int state, int id) {
        Map<String, Object> map = new HashMap<>();
        boolean result = userDao.updateUserStateById(state, id);
        map.put("result", result);
        map.put("flag", 1);
        return map;
    }

    @Override
    public Map<String, Object> loginOut() {
        Map<String, Object> map = new HashMap<>();
        //1、得到主体
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        map.put("msg", "退出成功");
        map.put("flag", 1);
        return map;
    }
}
