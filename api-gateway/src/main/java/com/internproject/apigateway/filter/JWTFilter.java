package com.internproject.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

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
                    URI location = URI.create("/login/authentication");
                    return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
                            .then(exchange.getResponse().setComplete())
                            .then(Mono.fromRunnable(() -> exchange.getResponse().getHeaders().set(HttpHeaders.LOCATION, location.toString())));
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    LOGGER.error("Missing Authorization Header");
                    URI location = URI.create("/login/authentication");
                    return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
                            .then(exchange.getResponse().setComplete())
                            .then(Mono.fromRunnable(() -> exchange.getResponse().getHeaders().set(HttpHeaders.LOCATION, location.toString())));
                }
            }

            return chain.filter(exchange);
        });
    }
}
