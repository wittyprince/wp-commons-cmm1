package io.github.wittyprince.commons.cmm1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Jwt
 *
 * @author WangChen
 * Created on 2024/10/12
 * @since 0.1
 */
@Component("jwtConfigProperties")
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigProperties {

    @Value("${jwt.secret: 123456789012345678901234567890ab}")
    private String secret;

    @Value("${jwt.ttl: 86400}")
    private Long ttl; // 秒

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }
}
