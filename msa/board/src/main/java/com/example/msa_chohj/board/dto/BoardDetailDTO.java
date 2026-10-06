package com.example.msa_chohj.board.dto;

import java.time.LocalDateTime;

public record BoardDetailDTO(String title, String content, LocalDateTime updatedDate) {
}
