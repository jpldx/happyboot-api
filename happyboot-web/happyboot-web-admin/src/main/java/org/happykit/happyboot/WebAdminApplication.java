package org.happykit.happyboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 启动类
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/3/4
 */
@Slf4j
@EnableCaching
@RestController
@SpringBootApplication(scanBasePackages = {"org.happykit.happyboot"})
public class WebAdminApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(WebAdminApplication.class, args);

        Environment env = application.getEnvironment();
//        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");

        log.info("\n*************************************************\n"
                + "**** Happyboot Application start-up success! ****\n"
                + "**** Port:" + port + "\t\t\tContext-Path:" + path + " ****\n"
                + "*************************************************\n");
    }

    @GetMapping("/")
    public String index() {
        return "HappyBoot System Api";
    }
}
