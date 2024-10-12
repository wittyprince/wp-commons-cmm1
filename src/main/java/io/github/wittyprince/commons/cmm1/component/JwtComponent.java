package io.github.wittyprince.commons.cmm1.component;

import io.github.wittyprince.commons.cmm1.config.JwtConfigProperties;
import io.github.wittyprince.commons.cmm1.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * JwtComponent
 *
 * @author WangChen
 * Created on 2024/6/7
 * @since 2.2
 */
@Component
public class JwtComponent {

    @Autowired
    private JwtConfigProperties jwtConfig;


    public String getJwtToken(Map<String, String> params) throws RuntimeException {
        return JwtUtils.generateJwtToken(params, jwtConfig.getTtl(), jwtConfig.getSecret());
    }

    public String getJwtToken(Map<String, String> params, String subject) throws RuntimeException {
        return JwtUtils.generateJwtToken(params, subject, jwtConfig.getTtl(), jwtConfig.getSecret());
    }

    public Claims parseJwtToken(String jwtToken) {
        return JwtUtils.parseJwtToken(jwtToken, jwtConfig.getSecret());
    }

    public Map<String, String> parseJwtTokenAsMap(String jwtToken) {
        return JwtUtils.parseJwtTokenAsMap(jwtToken, jwtConfig.getSecret());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtConfig.getSecret()).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
