package com.internproject.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> whiteList = List.of(
            "/auth/register",
            "/auth/login",
            "/auth/reset-password",
            "/eureka",
            "/auth/importRoles",
            "/auth/importUsers"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> whiteList.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
