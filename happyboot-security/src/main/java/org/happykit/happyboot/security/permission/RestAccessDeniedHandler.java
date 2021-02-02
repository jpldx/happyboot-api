package org.happykit.happyboot.security.permission;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当访问接口没有权限时，自定义的返回结果
 *
 * @author shaoqiang
 * @date 2020/1/16 18:40
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestAccessDeniedHandler.class);

    @Override
    public void handle(
            HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException e) throws IOException, ServletException {
//		logger.debug("当访问接口没有权限", e);
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("application/json;charset=utf-8");
//		ObjectMapper mapper = new ObjectMapper();
//		String result = mapper.writeValueAsString(R.failed(e.getMessage()));
//		response.getWriter().write(result);
//
//		ResponseUtils.out(response, R.failed("接口未授权"));
        ResponseUtils.out(response, R.failed(e.getMessage()));
    }
}
