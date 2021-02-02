package org.happykit.happyboot.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author shaoqiang
 * @version 1.0 2020/6/17
 */
public class ResponseUtils {

    private static final Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

    /**
     * 使用response输出JSON
     *
     * @param response
     * @param object
     */
    public static void out(HttpServletResponse response, Object object) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();

        // null值过滤
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 解决json时间类型转换失败问题
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String result = mapper.writeValueAsString(object);
        PrintWriter out = response.getWriter();
        out.println(result);
        out.flush();
        out.close();
    }
}
