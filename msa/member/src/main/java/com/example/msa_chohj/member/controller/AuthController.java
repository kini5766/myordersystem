package com.example.msa_chohj.member.controller;

import com.example.msa_chohj.member.dto.AuthTokenResponseDTO;
import com.example.msa_chohj.member.dto.RefreshDTO;
import com.example.msa_chohj.security.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    public static final String LOGIN_URL = "/member/login";

    @PostMapping("/member/cookie/exchange")
    public ResponseEntity<?> exchange(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        AuthTokenResponseDTO result = authService.cookie2Header(cookies);
        response.addHeader(HttpHeaders.SET_COOKIE, authService.emptyCookieRefreshToken().toString());
        return ResponseEntity.ok().body(result);
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
}
