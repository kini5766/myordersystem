package com.example.msa_chohj.board.dto;

import java.time.LocalDateTime;

public record BoardPostDTO(Long boardNo, int displayOrder, LocalDateTime displayStartDate, LocalDateTime displayEndDate, boolean isActive) {
}
