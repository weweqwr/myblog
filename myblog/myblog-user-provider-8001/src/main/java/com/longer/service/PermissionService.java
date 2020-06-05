package com.longer.service;

import com.longer.entities.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionService {
    /**
     * 根据账号查询账号信息
     * @param  account String
     * @author longer
     * @date 2020/4/19
     */
    List<String> selectPermissionByAccount(@Param("account")String account);
}
