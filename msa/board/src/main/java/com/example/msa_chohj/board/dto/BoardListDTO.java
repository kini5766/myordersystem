package com.example.msa_chohj.board.dto;

import java.time.LocalDateTime;

public record BoardListDTO(Long boardNo, String title, LocalDateTime updatedDate) {
}
