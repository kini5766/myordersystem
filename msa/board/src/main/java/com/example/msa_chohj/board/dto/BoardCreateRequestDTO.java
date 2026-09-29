package com.example.msa_chohj.board.dto;

import com.example.msa_chohj.board.domain.BoardType;

import java.time.LocalDateTime;

public record BoardCreateRequestDTO(
        String title,
        String content,
        BoardType boardType,
        int displayOrder,
        LocalDateTime displayStartDate,
        LocalDateTime displayEndDate,
        boolean active) {
}
