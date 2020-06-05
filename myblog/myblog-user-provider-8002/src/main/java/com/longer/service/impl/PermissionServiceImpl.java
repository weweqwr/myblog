package com.longer.service.impl;

import com.longer.entities.Permission;
import com.longer.mapperDao.PermissionDao;
import com.longer.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionDao permissionDao;

    @Override
    public List<String> selectPermissionByAccount(String account) {
        List<Permission> permissionList=permissionDao.selectPermissionByAccount(account);
        List<String> permissions=new ArrayList<>();
        for (Permission permission:permissionList){
            permissions.add(permission.getPercode());
        }
        return  permissions;
    }
}
