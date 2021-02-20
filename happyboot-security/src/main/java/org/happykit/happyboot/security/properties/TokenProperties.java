package org.happykit.happyboot.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author shaoqiang
 * @version 1.0 2020/6/17
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenProperties {
//	/**
//	 * 使用redis存储token
//	 */
//	private Boolean redis = true;

    /**
     * 单设备登陆
     */
    private Boolean sdl = true;

//	/**
//	 * 存储权限数据
//	 */
//	private Boolean storePerms = true;

    /**
     * token默认过期时间
     */
    private Integer tokenExpireTime = 30;

    /**
     * 用户选择保存登录状态对应token过期时间（天）
     */
    private Integer saveLoginTime = 7;

    /**
     * 客户端登录失败限制（次）
     */
    private Integer loginFailedLimit = 5;

    /**
     * 客户端登录失败限制恢复时间（分钟）
     */
    private Integer loginFailedLimitRecoverTime = 10;

//	/**
//	 * 密钥
//	 */
//	private String secret = "123456";

    /**
     * 头部
     */
    private String authorization = "Authorization";

    /**
     * 客户端标识
     */
    private String clientId = "ClientId";
//	/**
//	 * 前缀
//	 */
//	private String bearer = "Bearer ";
    /**
     * 登录密码是否加密
     */
    private Boolean lpe = false;

    /**
     * 登录密码加密key
     */
    private String lpeKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAL/M9+yfBpKMneipwiD29KJzm4JArV6nQzDJ5j3Dc9EOTt2BIwljZv9/sP7V9CwrQUNTfBPAlR44585x+NtDJ63gG2LA2jnzDaTID68KjOG7gFRIgBZ2mbfsXgA5pFp03w/a/9zAKciUSe5pIb9W0KPbAGU8wwBdzFrOP09YhR/NAgMBAAECgYBlv8e6eHaVIhHXTs9Ui44l7CyQQd13PEZxyHnjRB/ZxLxj19ENdvU6D7SGzFv3Xo8Ft3E4TU8ONGQM6ft53jtanvj5IZPabk/lkpge8L6WqSxnYcWJNWxcJJBSuDk5Z4U0nW0FZNM0tlBGPppC+rNSDanO3tgrZ3lpIn7FIrBL5QJBAPaVXGbW+HiqrUZCapf+aw6/FfG3RrK26jmMWRv28QhmTUE7y9oay2FQIN/uFJ/Tp7gm+dt6Mc1w0h6+BVuKcMMCQQDHIArTDuWoDSfdmFQh33T5xGX1q5+jkHDXJ0dQuGDmHcr7mPDCTEUXMbNy25rVGhgHSDfxScqvoYC/pWbA1SQvAkAm1itxfxYvWyJjWH6VZdrSvcHlCiq2ZxzI55P5VZFs8z/jsFlRBrtVnlsvKb5R1fIqjOj5amuBoe1WLjOF0W4lAkBH7VobnQD18DKbR9/0EFyXsArIcAMNOSqZfTW0gbV2ygI9WaR1+sjmNOzGK29FVNSjJMIYZXhScrsn/t4b/6G/AkBsM9ZTRT0T+O38g2OALwlrYY+tE3Cx3tv7SkSUSMKr9Vyry8aDR9wjHY73MfIjP93Mu3TNJZm/NV6vwOYgzEan";
}

