package com.internproject.userservice.inteceptor;

import com.internproject.userservice.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class InterceptorConfig implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getServletPath().contains("/auth/login")) {
            return true;
        }
        String userId = getUserIdFromRequest(request);
        String methodName = getMethodName(handler);
        String logMessage = "User ID: " + userId + " | Method: " + methodName + " | ";
        logger.info(logMessage + "START");
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
            logger.info(logMessage + "END");
        } else {
            logger.error(logMessage + "ERROR: " + ex.getMessage());
        }
    }

    private String getUserIdFromRequest(HttpServletRequest request) {
        return jwtUtils.getIdFromJwtToken(request.getHeader("Authorization"));
    }

    private String getMethodName(Object handler) {
        return handler.toString();
    }
}
