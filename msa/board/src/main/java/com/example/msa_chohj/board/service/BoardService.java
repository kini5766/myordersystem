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
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardDisplayPostRepository boardDisplayPostRepository;

    public BoardDetailDTO boardDetail(Long no) {
        Optional<Board> optionalBoard = boardRepository.findById(no);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            return new BoardDetailDTO(board.getBoardType(), board.getTitle(), board.getContent());
        } else {
            throw new EntityNotFoundException("Board with no " + no + " not found");
        }
    }

    public List<BoardListDTO> boardDisplayList(BoardType boardType) {
        return boardDisplayPostRepository.findDisplayPosts(boardType, LocalDateTime.now())
                .stream().map(post -> new BoardListDTO(post.getBoard().getNo(), post.getBoard().getTitle()))
                .toList();
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

        BoardDisplayPost displayPost = boardDisplayPostRepository.findByBoard_No(no)
                .orsave

        displayPost.modify(
                dto.displayOrder(),
                dto.displayStartDate(),
                dto.displayEndDate(),
                dto.active()
        );

        return board.getNo();
    }

    public void boardDelete(Long no) {
        boardDisplayPostRepository.deleteByBoard_No(no);
        boardRepository.deleteById(no);
    }

    public List<BoardListDTO> boardList(BoardType boardType) {
        return boardRepository.findByBoardType(boardType)
                .stream().map(board -> new BoardListDTO(board.getNo(), board.getTitle()))
                .toList();
    }

    public List<BoardPostDTO> boardPostList(BoardType boardType) {
        return boardDisplayPostRepository.findByBoard_BoardType(boardType)
                .stream().map(post -> new BoardPostDTO(
                        post.getBoard().getNo(),
                        post.getDisplayOrder(),
                        post.getDisplayStartDate(),
                        post.getDisplayEndDate(),
                        post.isActive()))
                .toList();
    }
}
