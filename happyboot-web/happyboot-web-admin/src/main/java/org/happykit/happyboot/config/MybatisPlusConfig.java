package org.happykit.happyboot.config;

import org.happykit.happyboot.mybatisplus.interceptor.DataAuthorityInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;

/**
 * @author shaoqiang
 * @date 2020/1/17 09:19
 */
@EnableTransactionManagement
@Configuration
@MapperScan(value = {"org.happykit.happyboot.**.mapper"})
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public SqlExplainInterceptor sqlExplainInterceptor() {
        // 启用执行分析插件
        return new SqlExplainInterceptor();
    }

    @Bean
    public DataAuthorityInterceptor dataAuthorityInterceptor() {
        // 启用数据权限插件
        return new DataAuthorityInterceptor();
    }

}
