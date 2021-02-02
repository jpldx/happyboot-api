package org.happykit.happyboot.security.login;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.constant.ResultCodeConstant;
import org.happykit.happyboot.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 当未登录或者token失效访问接口时，自定义的返回结果
 * 用来解决匿名用户访问无权限资源时的异常
 *
 * @author shaoqiang
 * @date 2020/1/16 18:50
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException e) throws IOException, ServletException {

        R<Object> r = new R<>();
        r.setCode(ResultCodeConstant.INVALID_TOKEN);
        r.setData(null);
        r.setMsg("登录状态已失效");
        ResponseUtils.out(response, r);
//		logger.debug("身份认证（登录）失败", e);
//		if (e instanceof InsufficientAuthenticationException) {
//			ResponseUtils.out(response, R.failed(SecurityErrorCode.UNAUTHORIZED));
//		} else {
//			ResponseUtils.out(response, R.failed(e.getMessage()));
//		}
    }
}
