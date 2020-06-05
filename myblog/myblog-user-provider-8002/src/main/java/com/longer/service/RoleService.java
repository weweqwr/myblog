package com.longer.service;

import com.longer.entities.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
    /**
     * 根据account查询账号的角色
     * @param account String
     * @author longer
     * @date 2020/4/19
     */
    List<String> selectRoleByAccount(@Param("account") String account);
}
