package org.happykit.happyboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shaoqiang
 * @version 1.0 2020/3/4
 */
@SpringBootApplication
@MapperScan("com.ltitframework.boot.generator.dao")
public class CodeGenerator {
    public static void main(String[] args) {
        SpringApplication.run(CodeGenerator.class, args);
    }
}
