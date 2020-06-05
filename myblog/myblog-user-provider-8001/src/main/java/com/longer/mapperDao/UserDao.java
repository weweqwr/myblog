package com.longer.mapperDao;

import com.longer.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserDao {
    /**
     * 用户注册
     *
     * @param account    String 账号
     * @param password   String 密码
     * @param createTime String 创建时间
     * @author longer
     * @date 2020/4/19
     */
    boolean insertUserRedist(@Param("account") String account, @Param("password") String password, @Param("createTime") String createTime);

    /**
     * 根据账号查询用户
     *
     * @param account String
     * @param roleid  int
     * @param state   int
     * @author longer
     * @date 2020/4/21
     */
    User selectUserByAccount(@Param("account") String account, @Param("roleid") int roleid, @Param("state") int state);

    /**
     * 修改个人信息
     * 参数太多，不做累赘
     *
     * @author longer
     * @date 2020/4/23
     */
    boolean updateUserInfo(@Param("avatarUrl") String avatarUrl,
                           @Param("nickname") String nickname,
                           @Param("name") String name,
                           @Param("gender") int gender,
                           @Param("province") String province,
                           @Param("city") String city,
                           @Param("district") String district,
                           @Param("maxim") String maxim,
                           @Param("introduct") String introduct,
                           @Param("account") String account);

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
    List<User> selectAdminUserBy(@Param("roleid") int roleid,
                                 @Param("time") int time,
                                 @Param("state") int state,
                                 @Param("curPage") int curPage,
                                 @Param("showCount") int showCount,
                                 @Param("account")String account);
    /**
     * 管理员账号
     * @param state    int
     * @param state    int
     * @author longer
     * @date 2020/5/12
     */
    boolean updateUserStateById(@Param("state")int state,@Param("id") int id);

    /**
     * 红人榜
     * 根据总浏览统计
     * @author longer
     * @date 2020/5/19
     */
    List<User> selectHotUser();
}
