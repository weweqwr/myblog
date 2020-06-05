package com.longer.mapperDao;

import com.longer.entities.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao {
    /**
     * 根据account查询账号的角色
     * @param account String
     * @author longer
     * @date 2020/4/19
     */
    List<Role> selectRoleByAccount(@Param("account") String account);

}
