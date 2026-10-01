package com.example.msa_chohj.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth")
public record AuthProperties(
        String accessTokenKeyId,
        String accessTokenPublic
) {
}