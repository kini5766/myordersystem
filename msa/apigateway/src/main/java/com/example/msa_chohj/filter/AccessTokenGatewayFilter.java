package com.example.msa_chohj.filter;

import com.example.msa_chohj.security.config.AuthProperties;
import com.example.msa_chohj.security.exception.AccessTokenRejectedException;
import com.example.msa_chohj.security.jwt.AccessTokenDecoder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AccessTokenGatewayFilter implements GlobalFilter {

    public static final String SUBJECT_HEADER = "X-User-Id";
    public static final String ROLES_HEADER = "X-User-Roles";
    private static final String ADMIN_ROLE = "ROLE_ADMIN";
    private static final String ADMIN_PATH = "/*/admin/**";

    private final AccessTokenDecoder decoder;
    private final List<PathPattern> excludePatterns;
    private final AntPathMatcher matcher = new AntPathMatcher();

    public AccessTokenGatewayFilter(AccessTokenDecoder decoder, AuthProperties authProperties) {
        this.decoder = decoder;
        PathPatternParser parser = new PathPatternParser();
        this.excludePatterns = authProperties.excludePaths().stream()
                .map(parser::parse)
                .toList();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (ignored(exchange)) {
            return chain.filter(withoutSpoofedIdentity(exchange));
        }

        String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = AccessTokenDecoder.stripBearer(bearerToken);
        String sub;
        List<String> roles;
        try {
            Jwt jwt = decoder.decode(token);
            sub = AccessTokenDecoder.requireSubject(jwt);
            roles = AccessTokenDecoder.rolesOf(jwt);
        } catch (AccessTokenRejectedException | JwtException ex) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        if (isAdminPath(exchange) && !roles.contains(ADMIN_ROLE)) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        ServerWebExchange modifiedExchange = exchange.mutate()
                .request(builder -> builder
                        .header(SUBJECT_HEADER, sub)
                        .header(ROLES_HEADER, rolesHeaderValue(roles))
                )
                .build();

        return chain.filter(modifiedExchange);
    }

//    private boolean ignored(ServerWebExchange exchange) {
//        PathContainer path = exchange.getRequest().getPath().pathWithinApplication();
//        return excludePatterns.stream().anyMatch(pattern -> pattern.matches(path));
//    }
    private boolean ignored(ServerWebExchange exchange) {
        PathContainer path = exchange.getRequest().getPath().pathWithinApplication();
        List<String> matched = excludePatterns.stream()
                .filter(pattern -> pattern.matches(path))
                .map(PathPattern::getPatternString)
                .toList();

        Logger log = LoggerFactory.getLogger(AccessTokenGatewayFilter.class);
        log.info("exclude check path={} matched={}", path.value(), matched);
        return !matched.isEmpty();
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

    private boolean isAdminPath(ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().pathWithinApplication().value();
        return matcher.match(ADMIN_PATH, path);
    }
}