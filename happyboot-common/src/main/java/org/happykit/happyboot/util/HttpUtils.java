package org.happykit.happyboot.util;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author shaoqiang
 * @version 1.0 2020/6/18
 */
public class HttpUtils {
    /**
     * 将URL的参数和body参数合并
     *
     * @param request
     * @author show
     * @date 14:24 2019/5/29
     */
    public static SortedMap<String, String> getAllParams(HttpServletRequest request) throws IOException {
        SortedMap<String, String> result = new TreeMap<>();

        /// 获取URL上的参数
		/*Map<String, String> urlParams = getUrlParams(request);
		for (Map.Entry entry : urlParams.entrySet()) {
			result.put((String) entry.getKey(), (String) entry.getValue());
		}*/

        Map<String, String> allRequestParam = new HashMap<>(16);
        // post请求拿body参数
        if (HttpMethod.POST.name().equals(request.getMethod())) {
            allRequestParam = getAllRequestParam(request);
        }
        //将URL的参数和body参数进行合并
        if (allRequestParam != null) {
            for (Map.Entry entry : allRequestParam.entrySet()) {
                result.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
        return result;
    }

    /**
     * 获取 Body 参数
     *
     * @param request
     * @author show
     * @date 15:04 2019/5/30
     */
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = "";
        // 一行一行的读取body体里面的内容；
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        if (StringUtils.isBlank(sb.toString())) {
            return new HashMap<>(0);
        }

        Gson gson = new Gson();
        return gson.fromJson(sb.toString(), Map.class);
    }
}
