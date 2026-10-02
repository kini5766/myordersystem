package com.example.msa_chohj.board.controller;

import com.example.msa_chohj.board.domain.BoardType;
import com.example.msa_chohj.board.dto.BoardCreateRequestDTO;
import com.example.msa_chohj.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list/{boardType}")
    public ResponseEntity<?> boardListApi(@PathVariable BoardType boardType) {
        return new ResponseEntity<>(boardService.boardDisplayList(boardType), HttpStatus.OK);
    }

    @GetMapping("/detail/{no}")
    public ResponseEntity<?> boardDetailApi(@PathVariable Long no) {
        return new ResponseEntity<>(boardService.boardDetail(no), HttpStatus.OK);
    }
}
