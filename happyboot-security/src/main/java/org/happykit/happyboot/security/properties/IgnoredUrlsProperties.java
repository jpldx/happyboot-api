package org.happykit.happyboot.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * url白名单
 *
 * @author shaoqiang
 * @author chen.xudong
 * @version 1.0 2020/3/10
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ignored")
public class IgnoredUrlsProperties {

    /**
     * Spring Security 免认证放行接口
     */
    private List<String> urls = new ArrayList<>();

    /**
     * ClientId 校验放行接口
     */
    private List<String> clientIdUrls = new ArrayList<>();

}
