package io.github.wittyprince.commons.cmm1.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JwtUtils
 *
 * @author WangChen
 * Created on 2024/6/7
 * @since 2.2
 */
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private JwtUtils() {
    }

    /**
     * 生成一个jwt token
     * @param params 参数
     * @param ttl 有效时间, 单位: 秒
     * @param secret 秘钥
     */
    public static String generateJwtToken(Map<String, String> params, long ttl, String secret) {
        JwtBuilder builder = getJwtBuilder(secret);
        addParams(builder, params, null, ttl);
        return builder.compact();
    }

    public static String generateJwtToken(Map<String, String> params, String subject, long ttl, String secret) {
        JwtBuilder builder = getJwtBuilder(secret);
        addParams(builder, params, subject, ttl);
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String secret) {
        if (secret == null) {
            throw new RuntimeException("generateJwtToken secret is null, please check!");
        }
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().setHeaderParam("typ", "JWT").signWith(secretKey);
    }

    private static void addParams(JwtBuilder builder, Map<String, String> params, String subject, long ttl) {
        //添加构成JWT的参数
        builder.setClaims(params).setSubject(subject);
        Date now = new Date();
        //添加Token过期时间
        if (ttl > 0) {
            long expMillis = now.getTime() + ttl * 1000;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
    }

    /**
     * 解析 jwt token
     */
    public static Claims parseJwtToken(String jwtToken, String secret) {
        if (secret == null) {
            throw new RuntimeException("parseJwtToken secret is null, please check!");
        }
        try {
            byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
            return Jwts.parserBuilder()
                    .setSigningKey(secretBytes)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (Exception e) {
            logger.error("==parseJwtToken: ", e);
//            e.printStackTrace();
            throw e;
        }
//        return null;
    }

    public static Map<String, String> parseJwtTokenAsMap(String jwtToken, String secret) {
        Claims claims = parseJwtToken(jwtToken, secret);
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map) claims;
        return map;
    }
}
