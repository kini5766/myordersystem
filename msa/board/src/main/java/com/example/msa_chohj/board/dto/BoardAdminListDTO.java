package com.example.msa_chohj.board.dto;

import com.example.msa_chohj.board.domain.BoardType;

import java.time.LocalDateTime;

public record BoardAdminListDTO(
        Long boardNo,
        BoardType boardType,
        String title,
        int displayOrder,
        LocalDateTime displayStartDate,
        LocalDateTime displayEndDate,
        boolean active
) {
}
