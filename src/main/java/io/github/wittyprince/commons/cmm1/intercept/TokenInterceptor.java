package io.github.wittyprince.commons.cmm1.intercept;

import io.github.wittyprince.commons.cmm1.component.JwtComponent;
import io.github.wittyprince.commons.cmm1.holder.UserContextHolder;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * TokenInterceptor
 *
 * @author WangChen
 * Created on 2024/6/7
 * @since 2.2
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    final static String ACCESS_TOKEN = "accessToken";

    @Resource
    private JwtComponent jwt;

    // 在访问接口之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("TokenInterceptor preHandle...");
        // return true 继续执行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader(ACCESS_TOKEN);
        if (token != null && !token.equals("")) {
            Claims claims;
            try {
                claims = jwt.parseJwtToken(token);
            } catch (Exception e) {
                logger.error("解析token异常: {}", e.getMessage(), e);
                throw new RuntimeException("token不正确或者已过期!");
            }
            if (claims != null) {
                String userId = claims.getSubject();
                Object roles = claims.get("roles");
                if (roles != null) {
                    UserContextHolder.setUser(userId, Arrays.asList(roles.toString().split(",")));
                } else {
                    UserContextHolder.setUser(userId, new ArrayList<>());
                }
//                UserContextHolder.setUser(userId, new ArrayList<>());
//                request.setAttribute("user_claims", claims);
                return true;
            }
        }
        throw new RuntimeException("token不正确或token为空!");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        UserContextHolder.clear();
    }
}
