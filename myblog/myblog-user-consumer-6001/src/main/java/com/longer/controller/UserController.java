package com.longer.controller;

import com.longer.entities.Article;
import com.longer.entities.SessionCons;
import com.longer.entities.User;
import com.longer.service.UserService;
import com.longer.utils.MultipartFileToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final String REST_URL_PREFIX = "http://MYBLOG-PROVIDER-USER/user/";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     *
     * @param user String 传account,password
     * @return map Map<String,Object> 返回注册信息
     * @author longer
     * @date 2020/4/19
     */
    @RequestMapping(value = "/insertUserRedist", method = RequestMethod.POST)
    public Map<String,Object> insertUserRedist(@RequestBody User user, HttpServletRequest request) {

        return this.userService.insertUserRedist(user);
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
    @RequestMapping(value = "/selectUserByAccount", method = RequestMethod.GET)
    Map<String,Object> selectUserByAccount(@RequestParam(value = "account") String account,
                                @RequestParam(value = "roleid") int roleid,
                                @RequestParam(value = "state") int state,
                                HttpServletResponse response,
                                HttpServletRequest request) {

        return this.userService.selectUserByAccount(account,roleid,state);
    }

   /*
     * 用户登录
     *
     * @param account  String
     * @param password string
     * @param roleid   int
     * @param state    int
     * @author longer
     * @date 2020/4/21
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.GET)
    ResponseEntity<String> doLogin(@RequestParam(value = "account") String account,
                                   @RequestParam(value = "password") String password,
                                   @RequestParam(value = "roleid") int roleid,
                                   @RequestParam(value = "state") int state,
                                   HttpServletResponse response,
                                   HttpServletRequest request,
                                   HttpSession session) {
        String url = REST_URL_PREFIX + "doLogin/" + account + "/" + password + "/" + roleid + "/" + state;
        HttpHeaders requestHeaders = new HttpHeaders();
        List<String> cookieList = new ArrayList<>();

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            cookieList=cookieList;
        }

        for (Cookie cookie : cookies) {
            cookieList.add(cookie.getName() + "=" + cookie.getValue());
        }

        requestHeaders.put(HttpHeaders.COOKIE,cookieList);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, requestEntity, String.class);


        return responseEntity;
    }
    /**
     * 修改个人信息
     * @param user User
     * @author longer
     * @date 2020/4/23
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    Map<String,Object> updateUserInfo(@RequestBody User user,
                                          HttpServletResponse response,
                                          HttpServletRequest request) throws Exception {
        return this.userService.updateUserInfo(user);
    }


    /**
     * 最近访客缓存进redis
     *
     * @param article Article
     * @author longer
     * @date 2020/5/2
     */
    @RequestMapping(value = "/cashRecentVisitors", method = RequestMethod.POST)
    Map<String,Object> cashRecentVisitors(@RequestBody Article article,
                                                     HttpServletResponse response,
                                                     HttpServletRequest request) {

        return this.userService.cashRecentVisitors(article);
    }
    /**
     * 查询最新访客redis
     * @param articleid int
     * @author longer
     * @date 2020/5/2
     */
    @RequestMapping(value = "/queryRecentVisitors", method = RequestMethod.GET)
    Map<String,Object> queryRecentVisitors(@RequestParam(value = "articleid") int articleid,
                                                  HttpServletResponse response,
                                                  HttpServletRequest request) {
        return this.userService.queryRecentVisitors(articleid);
    }


    /**
     * 管理员查询用户列表
     * @param roleid roleid
     * @param time time
     * @param state state
     * @param curPage curPage
     * @param showCount showCount
     * @author longer
     * @date 2020/5/12
     * */
    @RequestMapping(value = "/selectAdminUserBy", method = RequestMethod.GET)
    Map<String,Object> selectAdminUserBy(@RequestParam(value = "roleid")int roleid,
                                                    @RequestParam(value = "time")int time,
                                                    @RequestParam(value = "state")int state,
                                                    @RequestParam(value = "curPage")int curPage,
                                                    @RequestParam(value = "showCount")int showCount,
                                                    @RequestParam(value = "account")String account,
                                                    HttpServletRequest request) {

        return this.userService.selectAdminUserBy(roleid,time,state,curPage,showCount,account);
    }

    /**
     * 管理员用户账号管理
     * @param user User
     * @author longer
     * @date 2020/5/12
     */
    @RequestMapping(value = "/updateUserStateById", method = RequestMethod.POST)
    public Map<String,Object> updateUserStateById(@RequestBody User user,HttpServletRequest request) {
      return this.userService.updateUserStateById(user);
    }
    /**
     * 退出登录
     * */
    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    public ResponseEntity<String> loginOut(HttpServletRequest request) {
        String url = REST_URL_PREFIX + "loginOut";
        System.out.println("有道这里了吗");
        HttpHeaders requestHeaders = new HttpHeaders();
        List<String> cookieList = new ArrayList<>();

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            cookieList=cookieList;
        }

        for (Cookie cookie : cookies) {
            cookieList.add(cookie.getName() + "=" + cookie.getValue());
        }

        requestHeaders.put(HttpHeaders.COOKIE,cookieList);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        return responseEntity;
    }
    /**
     * 红人榜
     * 根据总浏览统计
     * @author longer
     * @date 2020/5/19
     */
    @RequestMapping(value = "/selectHotUser", method = RequestMethod.GET)
    public Map<String, Object> selectHotUser() {
        return this.userService.selectHotUser();
    }
}
