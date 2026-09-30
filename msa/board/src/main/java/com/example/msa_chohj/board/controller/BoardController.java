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

    // -- admin service --

    @GetMapping("/admin/list")
    public ResponseEntity<?> boardListAdmin() {
        return new ResponseEntity<>(boardService.boardAdminList(), HttpStatus.OK);
    }

    @GetMapping("/admin/detail/{no}")
    public ResponseEntity<?> boardDetailAdmin(@PathVariable Long no) {
        return new ResponseEntity<>(boardService.boardAdminDetail(no), HttpStatus.OK);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<?> boardCreateAdmin(@RequestBody BoardCreateRequestDTO dto) {
        return new ResponseEntity<>(boardService.boardCreate(dto), HttpStatus.CREATED);
    }

    @PutMapping("/admin/modifiy/{no}")
    public ResponseEntity<?> productModifyAdmin(@PathVariable Long no, @RequestBody BoardCreateRequestDTO dto) {
        return new ResponseEntity<>(boardService.boardModify(no, dto), HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{no}")
    public ResponseEntity<?> productDeleteAdmin(@PathVariable Long no) {
        boardService.boardDelete(no);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    // -- service to service --
}
