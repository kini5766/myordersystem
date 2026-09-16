package com.example.msa_chohj.member.controller;

import com.example.msa_chohj.member.dto.AuthTokenResponseDTO;
import com.example.msa_chohj.member.dto.RefreshDTO;
import com.example.msa_chohj.security.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    public static final String LOGIN_URL = "/member/login";

    private final AuthService authService;

    @PostMapping(value = "/member/cookie/exchange", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> exchange(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        AuthTokenResponseDTO result = authService.cookie2Header(cookies);
        response.addCookie(authService.emptyCookie());
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value = "/member/refresh", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> jwtRefreshApi(@Validated @RequestBody RefreshDTO dto) {
        return ResponseEntity.ok(authService.refreshRotate(dto));
    }

    @PostMapping("/member/logout")
    public ResponseEntity<?> logoutApi(@RequestBody RefreshDTO dto) {
        authService.removeRefresh(dto.refreshToken());
        return ResponseEntity.ok().build();
    }
}
