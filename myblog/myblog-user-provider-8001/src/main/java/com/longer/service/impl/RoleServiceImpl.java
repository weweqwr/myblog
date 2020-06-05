package com.longer.service.impl;

import com.longer.entities.Role;
import com.longer.mapperDao.RoleDao;
import com.longer.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;

    @Override
    public List<String> selectRoleByAccount(String account) {
        List<Role> roleList=roleDao.selectRoleByAccount(account);
        List<String> roles=new ArrayList<>();
        for(Role role:roleList){
            roles.add(role.getRolename());
        }
        return roles;
    }
}
