package io.github.alwilda.config;

import io.github.alwilda.consts.StringPool;
import io.github.alwilda.domain.web.Result;
import io.github.alwilda.exception.ServiceException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 接口异常处理。
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @InitBinder
    public void customizeBinding(WebDataBinder binder) {
        // 设置字符串去除前后空格
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    /**
     * 登录异常。
     */
    @ExceptionHandler(AuthenticationException.class)
    public Result<Void> handleAuthenticationException(AuthenticationException e) {
        String message = e.getMessage();
        if (message == null) {
            switch (e) {
                case UnknownAccountException ignored -> message = "账号不存在";
                case LockedAccountException ignored -> message = "账号被锁定";
                case DisabledAccountException ignored -> message = "账号被禁用";
                case IncorrectCredentialsException ignored -> message = "密码错误";
                default -> {
                    log.warn(e.getMessage(), e);
                    message = "身份验证错误";
                }
            }
        }
        return Result.fail(message);
    }

    /**
     * 鉴权异常。
     *
     * @see UnauthorizedException
     * @see UnauthenticatedException
     */
    @ExceptionHandler(AuthorizationException.class)
    public Result<Void> handleAuthorizationException(AuthorizationException e, HttpServletResponse response) {
        int status;
        String message;
        if (e instanceof UnauthorizedException) {
            status = 403;
            message = StringPool.PERMISSION_DENIED;
        } else {
            status = 401;
            message = StringPool.ACCESS_DENIED;
        }
        response.setStatus(status);
        return Result.fail(message);
    }

    /**
     * ServiceException.
     */
    @ExceptionHandler(ServiceException.class)
    public Result<Void> handleException(ServiceException e) {
        return Result.fail(e.getMessage());
    }

    /**
     * 最后捕获所有未知的异常。
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.fail("Internal Server Error");
    }
}
