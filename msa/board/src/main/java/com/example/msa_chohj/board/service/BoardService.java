package com.example.msa_chohj.board.service;

import com.example.msa_chohj.board.domain.Board;
import com.example.msa_chohj.board.domain.BoardDisplayPost;
import com.example.msa_chohj.board.domain.BoardType;
import com.example.msa_chohj.board.dto.*;
import com.example.msa_chohj.board.repository.BoardDisplayPostRepository;
import com.example.msa_chohj.board.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardDisplayPostRepository boardDisplayPostRepository;

    public BoardDetailDTO boardDetail(Long no) {
        Board board = boardRepository.findById(no)
                .orElseThrow(EntityNotFoundException::new);
        return new BoardDetailDTO(
                board.getTitle(),
                board.getContent(),
                board.getUpdatedDate()
        );
    }

    public List<BoardListDTO> boardDisplayList(BoardType boardType) {
        return boardDisplayPostRepository.findDisplayPosts(boardType, LocalDateTime.now())
                .stream().map(post -> new BoardListDTO(
                        post.getBoard().getNo(),
                        post.getBoard().getTitle(),
                        post.getBoard().getUpdatedDate())
                )
                .toList();
    }


    public BoardAdminDetailDTO boardAdminDetail(Long no) {
        Board board = boardRepository.findById(no)
                .orElseThrow(EntityNotFoundException::new);
        BoardDisplayPost displayPost = boardDisplayPostRepository.findByBoard_No(board.getNo())
                .orElseThrow(EntityNotFoundException::new);
        return new BoardAdminDetailDTO(
                board.getNo(),
                board.getBoardType(),
                board.getTitle(),
                board.getContent(),
                displayPost.getDisplayOrder(),
                displayPost.getDisplayStartDate(),
                displayPost.getDisplayEndDate(),
                displayPost.isActive()
        );
    }

    public List<BoardAdminListDTO> boardAdminList() {
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream().map(board -> {
            BoardDisplayPost displayPost = boardDisplayPostRepository.findByBoard_No(board.getNo())
                    .orElseThrow(EntityNotFoundException::new);
            return new BoardAdminListDTO(
                board.getNo(),
                board.getBoardType(),
                board.getTitle(),
                displayPost.getDisplayOrder(),
                displayPost.getDisplayStartDate(),
                displayPost.getDisplayEndDate(),
                displayPost.isActive()
            );
        }).toList();
    }

    public Long boardCreate(BoardCreateRequestDTO dto) {
        Board board = boardRepository.save(Board
                .builder()
                .title(dto.title())
                .content(dto.content())
                .boardType(dto.boardType())
                .build()
        );
        boardDisplayPostRepository.save(BoardDisplayPost
                .builder()
                .board(board)
                .displayOrder(dto.displayOrder())
                .displayStartDate(dto.displayStartDate())
                .displayEndDate(dto.displayEndDate())
                .isActive(dto.active())
                .build());
        return board.getNo();
    }

    public Long boardModify(Long no, BoardCreateRequestDTO dto) {
        Board board = boardRepository.findById(no)
                .orElseThrow(EntityNotFoundException::new);

        board.modify(
                dto.title(),
                dto.content(),
                dto.boardType()
        );

        Optional<BoardDisplayPost> optional = boardDisplayPostRepository.findByBoard_No(no);
        BoardDisplayPost displayPost;
        if (optional.isEmpty()) {
            displayPost = BoardDisplayPost.builder()
                    .board(board)
                    .displayStartDate(dto.displayStartDate())
                    .displayEndDate(dto.displayEndDate())
                    .isActive(dto.active())
                    .build();
            boardDisplayPostRepository.save(displayPost);
        } else {
            displayPost = optional.get();
            displayPost.modify(
                    dto.displayOrder(),
                    dto.displayStartDate(),
                    dto.displayEndDate(),
                    dto.active()
            );
        }

        return board.getNo();
    }

    public void boardDelete(Long no) {
        boardDisplayPostRepository.deleteByBoard_No(no);
        boardRepository.deleteById(no);
    }
}
