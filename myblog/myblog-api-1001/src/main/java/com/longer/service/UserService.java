package com.longer.service;

import com.longer.entities.Article;
import com.longer.entities.User;
import com.longer.fallback.UserServiceFallback;
import feign.Body;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@FeignClient(value = "MYBLOG-PROVIDER-USER",fallbackFactory = UserServiceFallback.class)
public interface UserService {

    /**
     * 用户注册
     *
     * @param user User 账号
     * @return map Map<String,Object> 返回注册信息
     * @author longer
     * @date 2020/4/19
     */
    @RequestMapping(value = "/user/insertUserRedist", method = RequestMethod.POST)
    public Map<String, Object> insertUserRedist(@RequestBody User user);

    /**
     * 根据账号查询用户信息
     *
     * @param account String
     * @param roleid  int
     * @param state   int
     * @author longer
     * @date 2020/4/21
     */
    @RequestMapping(value = "/user/selectUserByAccount", method = RequestMethod.GET)
    public  Map<String, Object> selectUserByAccount(@RequestParam(value = "account") String account,
                                            @RequestParam(value = "roleid") int roleid,
                                            @RequestParam(value = "state") int state);

    /**
     * 用户登录
     *
     * @param account  String
     * @param password String
     * @param roleid   int
     * @param state    int
     * @author longer
     * @date 2020/4/19
     */
    @RequestMapping(value = "/user/doLogin/{account}/{password}/{roleid}/{state}", method = RequestMethod.GET)
    public Map<String, Object> doLogin(@PathVariable("account") String account,
                                       @PathVariable("password") String password,
                                       @PathVariable("roleid") int roleid,
                                       @PathVariable("state") int state);

    /**
     * 修改个人信息
     * 参数太多，不做累赘
     *
     * @author longer
     * @date 2020/4/23
     */
    @RequestMapping(value = "/user/updateUserInfo", method = RequestMethod.POST)
    public Map<String, Object> updateUserInfo(@RequestBody User user);

    /**
     * 最近访客缓存进redis
     *
     * @param article Article
     * @author longer
     * @date 2020/5/2
     */
    @RequestMapping(value = "/user/cashRecentVisitors", method = RequestMethod.POST)
    public Map<String, Object> cashRecentVisitors(@RequestBody Article article);

    /**
     * 查询最新访客redis
     * @param articleid int
     * @author longer
     * @date 2020/5/2
     */
    @RequestMapping(value = "/user/queryRecentVisitors", method = RequestMethod.GET)
    public Map<String, Object> queryRecentVisitors(@RequestParam(value = "articleid") int articleid);

    /**
     * 管理员查询用户列表
     * @param roleid    int
     * @param time      int
     * @param state     int
     * @param curPage   int
     * @param showCount int
     * @param account   String
     * @author longer
     * @date 2020/5/12
     */
    @RequestMapping(value = "/user/selectAdminUserBy", method = RequestMethod.GET)
    public Map<String, Object> selectAdminUserBy(@RequestParam(value = "roleid") int roleid,
                                                 @RequestParam(value = "time") int time,
                                                 @RequestParam(value = "state") int state,
                                                 @RequestParam(value = "curPage") int curPage,
                                                 @RequestParam(value = "showCount") int showCount,
                                                 @RequestParam(value = "account") String account);

    /**
     * 管理员用户账号管理
     * @param user User
     * @author longer
     * @date 2020/5/12
     */
    @RequestMapping(value = "/user/updateUserStateById", method = RequestMethod.POST)
    public Map<String, Object> updateUserStateById(@RequestBody User user);

    /**
     * 红人榜
     * 根据总浏览统计
     * @author longer
     * @date 2020/5/19
     */
    @RequestMapping(value = "/user/selectHotUser", method = RequestMethod.GET)
    public Map<String, Object> selectHotUser();

}
