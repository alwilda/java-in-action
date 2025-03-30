package io.github.alwilda.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.alwilda.domain.entity.User;

public interface UserMapper extends BaseMapper<User> {

    default User selectByUsername(String username) {
        return selectOne(Wrappers.lambdaQuery(User.class)
                .eq(User::getUsername, username));
    }

}
