package org.happykit.happyboot.config;

import org.happykit.happyboot.security.login.CustomAuthenticationFilter;
import org.happykit.happyboot.security.login.JwtAuthenticationTokenFilter;
import org.happykit.happyboot.security.login.RestAuthenticationEntryPoint;
import org.happykit.happyboot.security.login.SecurityClientIdFilter;
import org.happykit.happyboot.security.permission.MyFilterSecurityInterceptor;
import org.happykit.happyboot.security.permission.RestAccessDeniedHandler;
import org.happykit.happyboot.security.properties.IgnoredUrlsProperties;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.happykit.happyboot.security.validate.CaptchaValidateFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;


/**
 * spring-security配置
 *
 * @author shaoqiang
 * @date 2020/1/16 16:58
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Qualifier("jwtUserDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private IgnoredUrlsProperties ignoredUrlsProperties;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Autowired
    private CaptchaValidateFilter captchaValidateFilter;

    @Autowired
    private SecurityClientIdFilter securityClientIdFilter;

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 资源角色配置登录
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        // 未登陆时返回 JSON
        // http.httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint);
        logger.info("url白名单");
        ignoredUrlsProperties.getUrls().forEach(s -> {
            logger.info(s);
            registry.antMatchers(s).permitAll();
        });
        // 对preflight ( OPTIONS 请求 ) 放行, 解决跨域 预请求权限认证问题
        registry.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and().formLogin()
                // csrf禁用，因为不使用session
                .and().csrf().disable().cors()
                // 定制我们自己的 session 策略
                // 调整为让 Spring Security 不创建和使用 session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(securityClientIdFilter, UsernamePasswordAuthenticationFilter.class)
                // 图形验证码过滤器
                .addFilterBefore(captchaValidateFilter, UsernamePasswordAuthenticationFilter.class)
                // 添加自定义权限过滤器
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                // 添加自定义权限过滤器
//				.addFilterAt(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                // json登录，用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
                // 自定义过滤器认证用户名密码
                .addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 添加过滤器 JWT 处理
//				.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 添加JWT认证过滤器
                .addFilter(new JwtAuthenticationTokenFilter(authenticationManager(), tokenProperties, redisTemplate))
                // 添加自定义异常入口
                .exceptionHandling()
                // 认证配置当用户请求了一个受保护的资源，但是用户没有通过登录认证
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                // 用户已经通过了登录认证，在访问一个受保护的资源，但是权限不够，则抛出授权异常，restAccessDeniedHandler类中handle()就会调用
                .accessDeniedHandler(restAccessDeniedHandler)
                // 防止iframe 造成跨域
                .and().headers().frameOptions().disable()
                // 禁用缓存
                .and().headers().cacheControl();
    }

    /**
     * 自定义登录方式
     *
     * @return
     * @throws Exception
     */
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        // 自定义登录方式, 采用 json 异步请求, 前后端分离，默认为表单登录
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        // 设置登陆接口名
//		filter.setFilterProcessesUrl(SecurityConstants.DEFAULT_LOGIN_URL);
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
