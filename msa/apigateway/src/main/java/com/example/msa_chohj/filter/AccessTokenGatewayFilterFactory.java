package com.example.msa_chohj.filter;

import com.example.msa_chohj.security.jwt.AccessTokenDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccessTokenGatewayFilterFactory
        extends AbstractGatewayFilterFactory<AccessTokenGatewayFilterFactory.Config> {

    private final AccessTokenDecoder decoder;

    @Override
    public GatewayFilter apply(Config config) {
        AccessTokenGatewayFilter delegate = new AccessTokenGatewayFilter(decoder, Config.IGNORE_PATHS);
        return delegate::filter;
    }

    public static class Config {

        private static final List<String> IGNORE_PATHS = List.of(
                "/member/create",
                "/member/login",
                "/member/cookie/exchange",
                "/member/refresh",
                "/product/list",
                "/member/exists/*",
                "/product/search/*",
                "/product/detail/*"
        );

    }
}