package com.example.msa_chohj.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class BoardController {
    @Autowired
    private BoardService service;

    // http://localhost:8081/api/v1/boardList
    @GetMapping("/boardList")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(service.boardList(), HttpStatus.OK);
    }

    // http://localhost:8081/api/v1/board
    @PostMapping("/board")
    public ResponseEntity<?> save(@RequestBody BoardDTO dto) {
        return new ResponseEntity<>(service.saveBoard(dto), HttpStatus.CREATED);
    }
}
