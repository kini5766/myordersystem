package com.example.msa_chohj.filter;

import com.example.msa_chohj.security.exception.AccessTokenRejectedException;
import com.example.msa_chohj.security.jwt.AccessTokenDecoder;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public class AccessTokenGatewayFilter implements GlobalFilter, Ordered {

    public static final String SUBJECT_HEADER = "X-User-Id";
    public static final String ROLES_HEADER = "X-User-Roles";

    private final AccessTokenDecoder decoder;
    private final List<String> ignorePaths;
    private final AntPathMatcher matcher = new AntPathMatcher();


    public AccessTokenGatewayFilter(AccessTokenDecoder decoder, List<String> ignorePaths) {
        this.decoder = decoder;
        this.ignorePaths = ignorePaths;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (ignored(exchange)) {
            return chain.filter(withoutSpoofedIdentity(exchange));
        }

        System.out.println("token 검증 시작");
        String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = AccessTokenDecoder.stripBearer(bearerToken);
        String sub;
        List<String> roles;
        try {
            Jwt jwt = decoder.decode(token);
            sub = AccessTokenDecoder.requireSubject(jwt);
            roles = AccessTokenDecoder.rolesOf(jwt);
        } catch (AccessTokenRejectedException ex) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        ServerWebExchange modifiedExchange = exchange.mutate()
                .request(builder -> builder
                        .header(SUBJECT_HEADER, sub)
                        .header(ROLES_HEADER, rolesHeaderValue(roles))
                )
                .build();
        return chain.filter(modifiedExchange);
//        ServerHttpRequest request = exchange.getRequest().mutate()
//                .headers(headers -> {
//                    headers.remove(SUBJECT_HEADER);
//                    headers.remove(ROLES_HEADER);
//                    headers.set(SUBJECT_HEADER, sub);
//                    headers.set(ROLES_HEADER, rolesHeaderValue(roles));
//                })
//                .build();
//        return chain.filter(exchange.mutate().request(request).build());
    }

    private boolean ignored(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        return ignorePaths.stream().anyMatch(pattern -> matcher.match(pattern, path));
    }

    private ServerWebExchange withoutSpoofedIdentity(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest().mutate()
                .headers(headers -> {
                    headers.remove(SUBJECT_HEADER);
                    headers.remove(ROLES_HEADER);
                })
                .build();
        return exchange.mutate().request(request).build();
    }

    private static String rolesHeaderValue(List<String> roles) {
        return String.join(",", roles);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
}