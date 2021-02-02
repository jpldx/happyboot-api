package org.happykit.happyboot.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * JWT 工具类
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/30
 */
@Slf4j
public class JwtUtils {

    private static final String JWT_ISSUER = "happyboot@ltit.info";

    /**
     * 生成token
     *
     * @param subject 主体
     * @param secret  秘钥
     * @return
     */
    public static String sign(String subject, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            return JWT.create()
                    .withSubject(subject)
                    .withIssuer(JWT_ISSUER)
                    .withIssuedAt(new Date())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            log.error("JWT create error,{}", e.getMessage());
            return null;
        }
    }

    /**
     * 解密
     *
     * @param token token
     * @return
     */
    public static DecodedJWT decode(String token) {
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException e) {
            log.error("JWT decode error,{}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取主体
     *
     * @param token token
     * @return
     */
    public static String getSubject(String token) {
        DecodedJWT decoded = decode(token);
        if (decoded != null) {
            return decoded.getSubject();
        }
        return null;
    }

    /**
     * 获取 JWT 类型 e.g. JWT
     *
     * @param token token
     * @return
     */
    public static String getType(String token) {
        DecodedJWT decoded = decode(token);
        if (decoded != null) {
            return decoded.getType();
        }
        return null;
    }

    /**
     * 获取发行人
     *
     * @param token token
     * @return
     */
    public static String getIssuer(String token) {
        DecodedJWT decoded = decode(token);
        if (decoded != null) {
            return decoded.getIssuer();
        }
        return null;
    }

    /**
     * 获取发行时间
     *
     * @param token token
     * @return
     */
    public static Date getIssueAt(String token) {
        DecodedJWT decoded = decode(token);
        if (decoded != null) {
            return decoded.getIssuedAt();
        }
        return null;
    }

    /**
     * 获取 JWT 加密算法 e.g. HS256
     *
     * @param token token
     * @return
     */
    public static String getAlgorithm(String token) {
        DecodedJWT decoded = decode(token);
        if (decoded != null) {
            return decoded.getAlgorithm();
        }
        return null;
    }

    public static void main(String[] args) {
        String token = sign("chenxudong", "chenxudong0926");
        System.out.println(token);
    }

}
