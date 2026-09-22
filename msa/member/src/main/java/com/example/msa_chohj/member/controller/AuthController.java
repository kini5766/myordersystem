package com.example.msa_chohj.member.controller;

import com.example.msa_chohj.common.config.AppProperties;
import com.example.msa_chohj.member.dto.RefreshDTO;
import com.example.msa_chohj.security.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppProperties appProperties;

    public static final String LOGIN_URL = "/member/login";

    @PostMapping("/member/cookie/exchange")
    public ResponseEntity<?> exchange(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new RuntimeException("쿠키가 존재하지 않습니다.");
        }
        String refreshToken = extractCookieRefreshToken(cookies);
        if (refreshToken == null) {
            throw new RuntimeException("refreshToken 쿠키가 존재하지 않습니다.");
        }
        response.addHeader(HttpHeaders.SET_COOKIE, emptyCookieRefreshToken().toString());
        return ResponseEntity.ok().body(new RefreshDTO(refreshToken));
    }

    @PostMapping(value = "/member/refresh", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> jwtRefreshApi(@Validated @RequestBody RefreshDTO dto) {
        return ResponseEntity.ok(authService.refreshRotate(dto));
    }

    @PostMapping(value = "/member/logout", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logoutApi(@Validated @RequestBody RefreshDTO dto) {
        authService.removeRefresh(dto.refreshToken());
        return ResponseEntity.ok().build();
    }

    private String extractCookieRefreshToken(Cookie[] cookies) {
        String name =  appProperties.cookie().refreshTokenName();
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private ResponseCookie emptyCookieRefreshToken() {
        return ResponseCookie.from(appProperties.cookie().refreshTokenName(), "")
                .httpOnly(true)
                .secure(appProperties.cookie().secure())
                .path("/")
                .maxAge(0)
                .sameSite(appProperties.cookie().sameSite())
                .build();
    }
}
