package com.longer.mapperDao;

import com.longer.entities.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionDao {
    /**
     * 根据账号查询账号信息
     * @param  account String
     * @author longer
     * @date 2020/4/19
    */
    List<Permission> selectPermissionByAccount(@Param("account") String account);



}
