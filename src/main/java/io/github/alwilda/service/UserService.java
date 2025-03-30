package io.github.alwilda.service;

import io.github.alwilda.domain.entity.User;
import io.github.alwilda.mapper.PermissionMapper;
import io.github.alwilda.mapper.RoleMapper;
import io.github.alwilda.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    public User getByUsername(String username) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return null;
        }

        Set<String> roles = roleMapper.selectUserRoles(user.getId());
        Set<String> permissions = permissionMapper.selectUserPermissions(user.getId());

        user.setRoles(roles);
        user.setPermissions(permissions);
        return user;
    }

}
