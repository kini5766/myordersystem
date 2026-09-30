package com.example.msa_chohj.board.dto;

import com.example.msa_chohj.board.domain.BoardType;

import java.time.LocalDateTime;

public record BoardAdminDetailDTO(
        Long boardNo,
        BoardType boardType,
        String title,
        String content,
        int displayOrder,
        LocalDateTime displayStartDate,
        LocalDateTime displayEndDate,
        boolean active
) {
}
