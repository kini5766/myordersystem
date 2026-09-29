package com.example.msa_chohj.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BoardDisplayPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_no", nullable = false)
    private Board board;

    @Column(nullable = false)
    private int displayOrder;

    // NULL 이면 즉시 출력
    private LocalDateTime displayStartDate;

    // NULL 이면 시간에 상관 없이 계속 출력
    private LocalDateTime displayEndDate;

    @Column(nullable = false)
    private boolean isActive;

    public void modify(
            int displayOrder,
            LocalDateTime displayStartDate,
            LocalDateTime displayEndDate,
            boolean active
    ) {
        this.displayOrder = displayOrder;
        this.displayStartDate = displayStartDate;
        this.displayEndDate = displayEndDate;
        this.isActive = active;
    }
}
