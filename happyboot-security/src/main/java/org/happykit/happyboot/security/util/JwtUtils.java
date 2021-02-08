package org.happykit.happyboot.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

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
public class JwtUtils {


    private static final String SECRET = "#e*!71&Sf@j#aA";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private static final String ISSUER = "979309838@qq.com";

    /**
     * 生成 token
     *
     * @param payload 负载信息
     * @return
     */
    public static String sign(Map<String, String> payload) {
        JWTCreator.Builder builder = JWT.create();
        payload.forEach(builder::withClaim);

        return builder.withIssuer(ISSUER)
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
}
