package com.example.msa_chohj.board.repository;

import com.example.msa_chohj.board.domain.BoardDisplayPost;
import com.example.msa_chohj.board.domain.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardDisplayPostRepository extends JpaRepository<BoardDisplayPost, Long> {
    List<BoardDisplayPost> findByBoard_BoardType(BoardType boardType);

    Optional<BoardDisplayPost> findByBoard_No(Long no);

    void deleteByBoard_No(Long boardNo);

    @Query("""
SELECT bdp
FROM BoardDisplayPost bdp
JOIN bdp.board b
WHERE b.boardType = :boardType
  AND bdp.isActive = true
  AND (bdp.displayStartDate IS NULL OR bdp.displayStartDate <= :now)
  AND (bdp.displayEndDate IS NULL OR bdp.displayEndDate >= :now)
ORDER BY bdp.displayOrder ASC
""")
    List<BoardDisplayPost> findDisplayPosts(
            @Param("boardType") BoardType boardType,
            @Param("now") LocalDateTime now
    );
}
