package com.example.msa_chohj.board.service;

import com.example.msa_chohj.board.domain.BoardEvent;
import com.example.msa_chohj.board.repository.BoardEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardEventService {
    private final BoardEventRepository boardEventRepository;

    public Long create(BoardEvent boardEvent) {
        return boardEventRepository.save(boardEvent).getId();
    }

    public BoardEvent detail(Long id) {
        return boardEventRepository.findById(id).orElse(null);
    }
}
