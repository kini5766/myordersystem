package com.example.msa_chohj.member.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshDTO(
        @NotBlank String refreshToken
) {
}