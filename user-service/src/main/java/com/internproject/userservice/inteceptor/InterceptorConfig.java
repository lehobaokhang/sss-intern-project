package com.internproject.userservice.inteceptor;

import com.internproject.userservice.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InterceptorConfig implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterceptorConfig.class);
    private static final List<String> nonInterceptorPath = List.of("/auth/login", "/auth/register", "/auth/reset-password", "/auth/importRoles", "/auth/importUsers");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (checkNonInterceptorPath(request.getServletPath())) {
            String userId = getUserIdFromRequest(request);
            String methodName = getMethodName(handler);
            String logMessage = "User ID: " + userId + " | Method: " + methodName + " | ";
            LOGGER.info("{}Start", logMessage);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (checkNonInterceptorPath(request.getServletPath())) {
            String userId = getUserIdFromRequest(request);
            String methodName = getMethodName(handler);
            boolean success = response.getStatus() < 400;
            String logMessage = "User ID: " + userId + " | Method: " + methodName + " | Success: " + success + " | ";
            if (ex == null) {
                LOGGER.info("{}END", logMessage);
            } else {
                LOGGER.error("{}ERROR: {}", logMessage, ex.getMessage());
            }
        }
    }

    private String getUserIdFromRequest(HttpServletRequest request) {
        return jwtUtils.getIdFromJwtToken(request.getHeader("Authorization"));
    }

    private String getMethodName(Object handler) {
        return handler.toString();
    }

    private boolean checkNonInterceptorPath(String request) {
        List<String> publicUrls = nonInterceptorPath.stream()
                .filter(path -> request.contains(path))
                .collect(Collectors.toList());
        return publicUrls.isEmpty();
    }
}
