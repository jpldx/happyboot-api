package org.happykit.happyboot.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 启动类
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/12
 */
@Slf4j
@RestController
@SpringBootApplication
public class MqConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqConsumerApplication.class, args);

        log.info("Happyboot MQ Consumer start-up success!");
    }

    @GetMapping("/")
    public String index() {
        return ">>> Happyboot MQ Consumer";
    }
}
