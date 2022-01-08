package com.quensty.serviceAcl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.quensty.serviceAcl.entity.Role;
import com.quensty.serviceAcl.entity.User;
import com.quensty.serviceAcl.service.IndexService;
import com.quensty.serviceAcl.service.PermissionService;
import com.quensty.serviceAcl.service.RoleService;
import com.quensty.serviceAcl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.selectByUsername(username);
        if (null == user) {
            //throw new GuliException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }

        //根据用户id获取角色
        List<Role> roleList = roleService.selectRoleByUserId(user.getId());
        List<String> roleNameList = roleList.stream().map(item -> item.getRoleName()).collect(Collectors.toList());
        if(roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }

        //根据用户id获取操作权限值
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        redisTemplate.opsForValue().set(username, permissionValueList);

        result.put("name", user.getUsername());
        result.put("avatar", "https://edu-demo-oss.oss-cn-beijing.aliyuncs.com/2021/11/02/19b4106a5ad74c13a7461017bc51c8d8file.png");
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }

    /**
     * 根据用户名获取动态菜单
     * @param username
     * @return
     */
    public List<JSONObject> getMenu(String username) {
        User user = userService.selectByUsername(username);

        //根据用户id获取用户菜单权限
        List<JSONObject> permissionList = permissionService.selectPermissionByUserId(user.getId());
        return permissionList;
    }


}
