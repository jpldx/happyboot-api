package org.happykit.happyboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动类
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/22
 */
@EnableCaching
@SpringBootApplication(scanBasePackages = {"org.happykit.happyboot"})
public class WebSocketApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class, args);
    }
}
