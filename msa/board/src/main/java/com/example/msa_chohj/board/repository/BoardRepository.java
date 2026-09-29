package com.example.msa_chohj.board.repository;

import com.example.msa_chohj.board.domain.Board;
import com.example.msa_chohj.board.domain.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByBoardType(BoardType boardType);
}
