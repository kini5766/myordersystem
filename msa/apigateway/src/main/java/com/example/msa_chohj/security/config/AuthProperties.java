package com.example.msa_chohj.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "auth")
public record AuthProperties(
        String accessTokenKeyId,
        String accessTokenPublic,
        List<String> excludePaths
) {
}