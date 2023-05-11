package com.internproject.apigateway.filter;

import com.internproject.apigateway.exception.JWTNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JWTFilter extends AbstractGatewayFilterFactory<JWTFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    public static class Config { }

    public JWTFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                // check jwt is in header or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new JWTNotFoundException("Missing Authorization Header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new JWTNotFoundException("Missing Authorization Header");
                }
            }

            return chain.filter(exchange);
        });
    }
}
