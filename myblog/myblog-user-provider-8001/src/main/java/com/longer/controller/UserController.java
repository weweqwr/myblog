package com.longer.controller;

import com.longer.entities.Article;
import com.longer.entities.User;
import com.longer.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import feign.Headers;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 用户注册
     *
     * @param user User 账号
     * @return map Map<String,Object> 返回注册信息
     * @author longer
     * @date 2020/4/19
     */
    @RequestMapping(value = "/insertUserRedist", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "processHystrix_Get")
    public Map<String, Object> insertUserRedist(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            String account = user.getAccount();
            String password = user.getPassword();
            map = userService.insertUserRedist(account, password);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("flag", 0); //返回0，表示代码抛出异常
            map.put("result", "系统繁忙，注册失败");
        }
        return map;
    }

    /**
     * 根据账号查询用户信息
     *
     * @param account String
     * @param roleid  int
     * @param state   int
     * @author longer
     * @date 2020/4/21
     */
    @RequestMapping(value = "/selectUserByAccount", method = RequestMethod.GET)
    Map<String, Object> selectUserByAccount(@RequestParam(value = "account") String account,
                                            @RequestParam(value = "roleid") int roleid,
                                            @RequestParam(value = "state") int state) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = userService.selectUserByAccount(account, roleid, state);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("flag", 0);
            map.put("result", "服务器繁忙");
        }
        return map;
    }

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
    @RequestMapping(value = "/doLogin/{account}/{password}/{roleid}/{state}", method = RequestMethod.GET)
    Map<String, Object> doLogin(@PathVariable("account") String account,
                                @PathVariable("password") String password,
                                @PathVariable("roleid") int roleid,
                                @PathVariable("state") int state,
                                HttpServletResponse response,
                                HttpServletRequest request,
                                HttpSession session) {
        System.out.println(response.getHeaderNames());
        System.out.println(request.getHeaderNames());
        Map<String, Object> map = new HashMap<>();
        try {
            map = userService.doLogin(account, password, roleid, state, response, request, session);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("flag", 0);
            map.put("result", "系统繁忙，登录失败");
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
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    Map<String, Object> updateUserInfo(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            userService.updateUserInfo(user.getAvatarUrl(), user.getNickname(), user.getName(), user.getGender(), user.getProvince(),
                    user.getCity(), user.getDistrict(), user.getMaxim(), user.getIntroduct(), user.getAccount());
            map.put("flag", 1);
            map.put("result", "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("flag", 1);
            map.put("result", "修改失败");
        }
        return map;
    }

    /**
     * 最近访客缓存进redis
     *
     * @param article Article
     * @author longer
     * @date 2020/5/2
     */
    @RequestMapping(value = "/cashRecentVisitors", method = RequestMethod.POST)
    public Map<String, Object> cashRecentVisitors(@RequestBody Article article) {
        Map<String, Object> map = new HashMap<>();
        try {
            userService.cashRecentVisitors(article.getId(), article.getAuthor());
            map.put("result", true);
            map.put("flag", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }

    /**
     * 查询最新访客redis
     *
     * @param articleid int
     * @author longer
     * @date 2020/5/2
     */
    @RequestMapping(value = "/queryRecentVisitors", method = RequestMethod.GET)
    public Map<String, Object> queryRecentVisitors(int articleid) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = userService.queryRecentVisitors(articleid);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }

    /**
     * 管理员查询用户列表
     *
     * @param roleid    int
     * @param time      int
     * @param state     int
     * @param curPage   int
     * @param showCount int
     * @param account   String
     * @author longer
     * @date 2020/5/12
     */
    @RequiresPermissions(value = {"admin:AdminPermission", "superAdmin:superAdminPermission"}, logical = Logical.OR)
    @RequestMapping(value = "/selectAdminUserBy", method = RequestMethod.GET)
    public Map<String, Object> selectAdminUserBy(int roleid, int time, int state, int curPage, int showCount, String account) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = userService.selectAdminUserBy(roleid, time, state, curPage, showCount, account);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }

    /**
     * 管理员用户账号管理
     *
     * @param user User
     * @author longer
     * @date 2020/5/12
     */
    @RequiresPermissions(value = {"admin:AdminPermission", "superAdmin:superAdminPermission"}, logical = Logical.OR)
    @RequestMapping(value = "/updateUserStateById", method = RequestMethod.POST)
    public Map<String, Object> updateUserStateById(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = userService.updateUserStateById(user.getState(), user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 1);
        }
        return map;
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    public Map<String, Object> loginOut(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = userService.loginOut(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }

    /**
     * 红人榜
     * 根据总浏览统计
     *
     * @author longer
     * @date 2020/5/19
     */
    @RequestMapping(value = "/selectHotUser", method = RequestMethod.GET)
    public Map<String, Object> selectHotUser() {
        Map<String, Object> map = new HashMap();
        try {
            map = userService.selectHotUser();
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }

}
