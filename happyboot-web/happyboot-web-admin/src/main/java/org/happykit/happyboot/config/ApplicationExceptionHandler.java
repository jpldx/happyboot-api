package org.happykit.happyboot.config;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.exception.SysException;
import org.happykit.happyboot.exception.SysSecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/8
 */
@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {
//    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public R handleRuntimeException(RuntimeException e) {
        log.error("服务器异常", e.getMessage());
        return R.failed("服务器异常");
    }

    @ExceptionHandler(SysException.class)
    public R handleSysException(SysException e) {
        log.error("系统异常", e.getMessage());
        return R.failed(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R handleAccessDeniedException(AccessDeniedException e) {
//        logger.error("通用的业务逻辑异常:", e);
        return R.failed(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public R handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().iterator().next().getMessage();
        return R.failed(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = bindingResult.getAllErrors().iterator().next().getDefaultMessage();
        return R.failed(message);
    }

    @ExceptionHandler(BindException.class)
    public R handleBindException(BindException e) {
        String message = e.getAllErrors().iterator().next().getDefaultMessage();
        return R.failed(message);
    }

    @ExceptionHandler(SysSecurityException.class)
    public R handleSysSecurityException(SysSecurityException e) {
        log.error("系统安全性异常", e.getMessage());
        return R.failed(e.getMessage());
    }
}
