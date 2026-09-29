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
        return new ResponseEntity<>(
                boardService.boardDisplayList(boardType),
                HttpStatus.OK
        );
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> boardDetailApi(@PathVariable Long id) {
        return new ResponseEntity<>(boardService.boardDetail(id), HttpStatus.OK);
    }

    // -- admin service --

    @PostMapping("/admin/create")
    public ResponseEntity<?> boardCreateAdmin(@RequestBody BoardCreateRequestDTO dto) {
        return new ResponseEntity<>(boardService.boardCreate(dto), HttpStatus.CREATED);
    }

    @PutMapping("/admin/modifiy/{id}")
    public ResponseEntity<?> productModifyAdmin(@PathVariable Long id, @RequestBody BoardCreateRequestDTO dto) {
        return new ResponseEntity<>(boardService.boardModify(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> productDeleteAdmin(@PathVariable Long id) {
        boardService.boardDelete(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/admin/list/{boardType}")
    public ResponseEntity<?> boardListAdmin(@PathVariable BoardType boardType) {
        return new ResponseEntity<>(boardService.boardList(boardType), HttpStatus.OK);
    }

    @GetMapping("/admin/post/{boardType}")
    public ResponseEntity<?> boardPostListAdmin(@PathVariable BoardType boardType) {
        return new ResponseEntity<>(boardService.boardPostList(boardType), HttpStatus.OK);
    }

    // -- service to service --
}
