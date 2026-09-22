package com.example.msa_chohj.security.handler;

import com.example.msa_chohj.common.config.AppProperties;
import com.example.msa_chohj.member.dto.AuthTokenResponseDTO;
import com.example.msa_chohj.security.service.AuthService;
import com.example.msa_chohj.security.config.AuthProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SocialSuccessHandler implements AuthenticationSuccessHandler {
    public static final String REDIRECT_LOCATION = "/cookie";

    private final AuthService authService;
    private final AppProperties appProperties;
    private final AuthProperties authProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // authentication.getName() => CustomOAuth2User 의 getName 과 동일한 값
        String sub = authentication.getName();

        AuthTokenResponseDTO tokens = authService.issueTokens(sub);

        ResponseCookie refreshCookie = refreshCookie(tokens.refreshToken());

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        response.sendRedirect(appProperties.frontend().url() + REDIRECT_LOCATION);
    }

    public ResponseCookie refreshCookie(String rawRefreshToken) {
        return ResponseCookie.from(appProperties.cookie().refreshTokenName(), rawRefreshToken)
                .httpOnly(true)
                .secure(appProperties.cookie().secure())
                .path("/")
                .maxAge(authProperties.refreshTokenExpiration())
                .sameSite(appProperties.cookie().sameSite())
                .build();
    }
}