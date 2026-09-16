package com.example.msa_chohj.member.dto;

public record AuthTokenResponseDTO(Long memberId, String accessToken, String refreshToken) {
}
