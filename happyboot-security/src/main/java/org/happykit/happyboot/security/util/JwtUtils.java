package org.happykit.happyboot.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/30
 */
@Slf4j
@Component
public class JwtUtils {

    @Autowired
    private TokenProperties tokenProperties;

    private static final String SECRET = "#e*!71&Sf@j#aA";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private static final String ISSUER = "979309838@qq.com";

    public static String CLAIM_USER_ID = "user_id";
    public static String CLAIM_USER_NAME = "user_name";
    public static String CLAIM_USER_TYPE = "user_type";
    public static String CLAIM_MAIN_ACCOUNT_ID = "main_account_id";


    /**
     * 生成 token
     *
     * @param payload 负载信息
     * @return
     */
    public String create(Map<String, String> payload) {
        JWTCreator.Builder builder = JWT.create();
        payload.forEach(builder::withClaim);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, tokenProperties.getTokenExpireTime());

        return builder.withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .withIssuedAt(new Date())
                .sign(ALGORITHM);
    }

    /**
     * 校验
     *
     * @param token
     * @return
     */
    public static DecodedJWT verify(String token) {
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        return verifier.verify(token);
    }

    /**
     * 解密
     *
     * @param token
     * @return
     */
    public static DecodedJWT decode(String token) {
        return JWT.decode(token);
    }

//    public static String getUsername(DecodedJWT decodedJWT){
//        return decodedJWT.getClaim("")
//    }

}
