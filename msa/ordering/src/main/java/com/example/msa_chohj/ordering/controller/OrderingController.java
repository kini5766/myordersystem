package com.example.msa_chohj.ordering.controller;

import com.example.msa_chohj.ordering.dto.OrderCreateDTO;
import com.example.msa_chohj.ordering.service.OrderingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ordering")
public class OrderingController {
    private final OrderingService orderingService;

    public OrderingController(OrderingService orderingService) {
        this.orderingService = orderingService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrderApi(@RequestBody OrderCreateDTO dto, @RequestHeader("X-User-Id") String memberId) {
        return new ResponseEntity<>(orderingService.orderCreate(dto, Long.parseLong(memberId)), HttpStatus.CREATED);
    }

    @GetMapping("/myList")
    public ResponseEntity<?> myOrderApi(@RequestHeader("X-User-Id") String memberId) {
        return new ResponseEntity<>(orderingService.myOrderingList(Long.parseLong(memberId)), HttpStatus.OK);
    }
}
