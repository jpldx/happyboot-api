package org.happykit.happyboot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author shaoqiang
 * @version 1.0 2020/3/30
 */
@ConfigurationProperties(prefix = "file.upload")
@Getter
@Setter
@Configuration
public class FileUploadConfig {
    /**
     * 是否保存到服务器
     */
    private Boolean storeToServer = true;

    /**
     * 保存的路径，只有在storeToServer为true时有效
     */
    private String storePath = "/temp";
}
