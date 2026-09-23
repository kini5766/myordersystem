package com.example.msa_chohj.board.repository;

import com.example.msa_chohj.board.domain.BoardNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardNotificationRepository extends JpaRepository<BoardNotification, Long> {
}
