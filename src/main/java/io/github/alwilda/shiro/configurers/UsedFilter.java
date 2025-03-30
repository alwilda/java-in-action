package io.github.alwilda.shiro.configurers;

import io.github.alwilda.shiro.filter.AuthenticationFilter;
import io.github.alwilda.shiro.filter.PermissionFilter;
import io.github.alwilda.shiro.filter.RememberedFilter;
import io.github.alwilda.shiro.filter.RoleFilter;
import io.github.alwilda.shiro.filter.jwt.JwtFilter;
import jakarta.servlet.Filter;
import lombok.Getter;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;

/**
 * 定义用到的过滤器名称。
 *
 * @see DefaultFilter
 */
@Getter
public enum UsedFilter {

    anon(AnonymousFilter.class),

    authc(AuthenticationFilter.class),

    perms(PermissionFilter.class),

    roles(RoleFilter.class),

    remember(RememberedFilter.class),

    jwt(JwtFilter.class);

    private final Class<? extends Filter> filterClass;

    UsedFilter(Class<? extends Filter> filterClass) {
        this.filterClass = filterClass;
    }
}
