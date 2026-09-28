package com.example.msa_chohj.board.domain;

import com.example.msa_chohj.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseBoard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @Column(nullable = false)
    private Long memberId;
}
