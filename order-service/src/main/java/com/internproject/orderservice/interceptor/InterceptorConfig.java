package com.internproject.orderservice.interceptor;

import com.internproject.orderservice.config.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class InterceptorConfig implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getServletPath().contains("/auth/login")) {
            String userId = getUserIdFromRequest(request);
            String methodName = getMethodName(handler);
            String logMessage = "User ID: " + userId + " | Method: " + methodName + " | ";
            logger.info("{}START", logMessage);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (request.getServletPath().contains("/auth/login")) {
            return;
        }
        String userId = getUserIdFromRequest(request);
        String methodName = getMethodName(handler);
        boolean success = response.getStatus() < 400;
        String logMessage = "User ID: " + userId + " | Method: " + methodName + " | Success: " + success + " | ";
        if (ex == null) {
            logger.info("{}END", logMessage);
        } else {
            logger.error("{}ERROR: {}",logMessage, ex.getMessage());
        }
    }

    private String getUserIdFromRequest(HttpServletRequest request) {
        return jwtUtils.getIdFromJwtToken(request.getHeader("Authorization"));
    }

    private String getMethodName(Object handler) {
        return handler.toString();
    }
}
