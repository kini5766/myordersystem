package com.example.msa_chohj.security.service;

import com.example.msa_chohj.member.dto.AuthTokenResponseDTO;
import com.example.msa_chohj.member.dto.RefreshDTO;
import com.example.msa_chohj.security.dao.RefreshTokenDAO;
import com.example.msa_chohj.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenDAO refreshTokenDAO;
    private final CustomUserDetailsService userDetailsService;

    private final UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();

    public void removeRefresh(String rawRefreshToken) {
        refreshTokenDAO.deleteTokenHash(rawRefreshToken);
    }

    public AuthTokenResponseDTO refreshRotate(RefreshDTO request) {
        String requestRawRefresh = request.refreshToken();

        Optional<String> optionalSub = refreshTokenDAO.findAndDelete(requestRawRefresh);
        if (optionalSub.isEmpty()) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String sub = optionalSub.get();
        long userId;
        try {
            userId = Long.parseLong(sub);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("잘못된 토큰입니다.");
        }

        UserDetails userDetails = findAndValidateUser(sub);

        String newRawRefresh = refreshTokenDAO.generateAndSave(sub);
        String newAccessToken = jwtTokenProvider.createToken(sub, userDetails.getAuthorities());
        return new AuthTokenResponseDTO(userId, newAccessToken, newRawRefresh);
    }

    public AuthTokenResponseDTO issueTokens(String sub) {
        UserDetails userDetails = findAndValidateUser(sub);
        long userId;
        try {
            userId = Long.parseLong(sub);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("아이디 혹은 비밀번호가 틀렸습니다.");
        }

        String newRawRefresh = refreshTokenDAO.generateAndSave(sub);
        String newAccessToken = jwtTokenProvider.createToken(sub, userDetails.getAuthorities());
        return new AuthTokenResponseDTO(userId, newAccessToken, newRawRefresh);
    }

    private UserDetails findAndValidateUser(String sub) {
        long userId;
        try {
            userId = Long.parseLong(sub);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("아이디 혹은 비밀번호가 틀렸습니다.");
        }
        UserDetails userDetails = userDetailsService.loadUserById(userId);
        userDetailsChecker.check(userDetails);
        return userDetails;
    }
}
