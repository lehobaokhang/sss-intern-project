package com.internproject.apigateway.filter;

import com.internproject.apigateway.exception.JWTNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class JWTFilter extends AbstractGatewayFilterFactory<JWTFilter.Config> {
    @Autowired
    private RouteValidator routeValidator;

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTFilter.class);

    public static class Config { }

    public JWTFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                ServerHttpRequest request = exchange.getRequest();
                HttpHeaders headers = request.getHeaders();

                // check jwt is in header or not
                if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                    LOGGER.error("Missing Authorization Header");
                    throw new JWTNotFoundException("Missing Authorization Header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    LOGGER.error("Missing Authorization Header");
                    throw new JWTNotFoundException("Missing Authorization Header");
                }
            }

            return chain.filter(exchange);
        });
    }
}
