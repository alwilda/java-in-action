package io.github.alwilda.controller;

import io.github.alwilda.domain.CurrentUser;
import io.github.alwilda.domain.web.Result;
import io.github.alwilda.exception.BadParameterException;
import io.github.alwilda.service.LoginService;
import io.github.alwilda.shiro.session.RedisSession;
import io.github.alwilda.util.WebUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static io.github.alwilda.util.DateUtils.SIMPLE_MILLI_FORMATTER;

@Slf4j
@RestController
public class AccountController {

    @Resource
    private LoginService loginService;

    @GetMapping("login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam(required = false, defaultValue = "false") Boolean rememberMe,
                        HttpServletRequest request) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new BadParameterException("The username and password can't be empty.");
        }
        String requestIp = WebUtils.getRequestIp(request);
        return loginService.login(new UsernamePasswordToken(username, password, rememberMe, requestIp));
    }

    @PostMapping("logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "🆗";
    }

    @GetMapping("info")
    public Result<CurrentUser> info() {
        Subject subject = SecurityUtils.getSubject();
        RedisSession session = (RedisSession) subject.getSession();

        Date lastAccessTime = session.getLastAccessTime();
        Date lastRememberedAccessTime = session.getLastRememberedAccessTime();

        log.info("lastAccessTime:           {}", SIMPLE_MILLI_FORMATTER.format(lastAccessTime));
        log.info("lastRememberedAccessTime: {}", lastRememberedAccessTime == null ? null : SIMPLE_MILLI_FORMATTER.format(lastRememberedAccessTime));

        return Result.ok((CurrentUser) subject.getPrincipal());
    }

    @GetMapping("home1")
    public String home1() {
        return "Welcome Home 1 😀";
    }

    @GetMapping("home2")
    @RequiresRoles("home2")
    public String home2() {
        return "Welcome Home 2 😃";
    }

    @GetMapping("home3")
    public String home3() {
        return "Welcome Home 3 😄";
    }

    @GetMapping("jwt")
    public String jwt() {
        return "JSON Web Token";
    }
}
