package com.example.msa_chohj.board.repository;

import com.example.msa_chohj.board.domain.BoardEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardEventRepository  extends JpaRepository<BoardEvent, Long> {
}
