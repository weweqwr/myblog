package com.longer.service;

import com.longer.entities.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 用户注册
     *
     * @param account  String 账号
     * @param password String 密码
     * @author longer
     * @date 2020/4/19
     */
    Map<String, Object> insertUserRedist(String account, String password);

    /**
     * 根据账号查询用户信息
     *
     * @param account 账号
     * @param roleid  int
     * @param state   int
     * @author longer
     * @date 2020/4/19
     */
    Map<String, Object> selectUserByAccount(String account, int roleid, int state);

    /**
     * 用户登录
     *
     * @param account String
     * @author longer
     * @date 2020/4/19
     */
    Map<String, Object> doLogin(String account, String Password, int roleid, int state,
                                HttpServletResponse response,
                                HttpServletRequest request,
                                HttpSession session);

    /**
     * 修改个人信息
     * 参数太多，不做累赘
     *
     * @author longer
     * @date 2020/4/23
     */
    boolean updateUserInfo(String avatarUrl, String nickname, String name,
                           int gender, String province, String city, String district,
                           String maxim, String introduct, String account);


    /**
     * 最近访客缓存进redis
     *
     * @param articleid int
     * @param account   账号
     * @author longer
     * @date 2020/5/2
     */
    void cashRecentVisitors(int articleid, String account);

    /**
     * 查询最新访客redis
     *
     * @param articleid int
     * @author longer
     * @date 2020/5/2
     */
    Map<String, Object> queryRecentVisitors(int articleid);

    /**
     * 管理员查询用户列表
     *
     * @param roleid    roleid
     * @param time      time
     * @param state     state
     * @param curPage   curPage
     * @param showCount showCount
     * @param account   String
     * @author longer
     * @date 2020/5/12
     */
    Map<String, Object> selectAdminUserBy(int roleid,
                                          int time,
                                          int state,
                                          int curPage,
                                          int showCount,
                                          String account);
    /**
     * 管理员用户账号管理
     * @param state    int
     * @param state    int
     * @author longer
     * @date 2020/5/12
     */
    Map<String, Object> updateUserStateById(@Param("state")int state,@Param("id") int id);
    /**
     * 退出登录
     * @author longer
     * @date 2020/5/18
     * */
    Map<String,Object> loginOut(HttpServletRequest request,HttpServletResponse response);
    /**
     * 红人榜
     * 根据总浏览统计
     * @author longer
     * @date 2020/5/19
     */
    Map<String,Object> selectHotUser();
}
