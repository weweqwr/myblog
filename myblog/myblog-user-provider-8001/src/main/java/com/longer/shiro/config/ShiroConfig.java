package com.longer.shiro.config;

import com.longer.config.redis.RedisTemplateConfig;
import com.longer.shiro.filter.MyFormAuthenticationFilter;
import com.longer.shiro.relam.UserRealm;
import com.longer.shiro.rewrite.RedisSessionDAO;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro安全控件配置文件
 * author longer
 * date 2020/4/20
 */
@Configuration
public class ShiroConfig {

    @Bean
    public RedisManager redisManager(RedisTemplateConfig redisConfig) {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisConfig.getOrderHost());
        redisManager.setPort(Integer.parseInt(redisConfig.getOrderPort()));
        redisManager.setPassword(redisConfig.getOrderPassword());
        redisManager.setExpire(60*60);// 配置缓存过期时间
        redisManager.setTimeout(Integer.parseInt(redisConfig.getOrderTimeout()));
        return redisManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * 注入凭证配置器
     */
    @Bean
    HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    /**
     * 自定义relam的验证方法注入到容器中
     */
    @Bean
    public UserRealm myUserRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        //注入凭证配置器
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }


    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    @Bean
    public DefaultWebSessionManager redisSessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    /**
     * 声明一个/cookie的对象
     */
    @Bean
    public SimpleCookie cookie() {
        SimpleCookie cookie = new SimpleCookie("remberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(604800);
        return cookie;
    }

    @Bean
    public CookieRememberMeManager cookieManager(SimpleCookie cookie) {
        CookieRememberMeManager cookieManager = new CookieRememberMeManager();
        cookieManager.setCookie(cookie);
        return cookieManager;
    }


    /**
     * 权限管理，配置主要是Realm的管理认证
     */
    @Bean
    public SecurityManager securityManager(UserRealm myUserRealm, DefaultWebSessionManager redisSessionManager, RedisCacheManager redisCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myUserRealm);
        securityManager.setSessionManager(redisSessionManager);
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }


    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        Map<String, Filter> maps = new HashMap<>();
//        maps.put("authc", new MyFormAuthenticationFilter());
//        maps.put("rememberMe", new RemberMeFilter());
        maps.put("authc", new MyFormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(maps);
        map.put("/user/doLogin/**", "anon");
        map.put("/user/insertUserRedist", "anon");
        map.put("/user/selectUserByAccount", "anon");
        map.put("/user/queryRecentVisitors", "anon");
        map.put("/user/selectHotUser", "anon");
        map.put("/**", "authc");
        // 未授权的跳转页
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    /**加入注解的使用，不加入这个注解不生效
     */
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
