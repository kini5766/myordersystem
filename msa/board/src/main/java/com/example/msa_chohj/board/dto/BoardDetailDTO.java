package com.example.msa_chohj.board.dto;

import com.example.msa_chohj.board.domain.BoardType;

public record BoardDetailDTO(BoardType boardType, String title, String content) {
}
