package com.example.msa_chohj.member.controller;

import com.example.msa_chohj.member.dto.MemberNameListRequestDTO;
import com.example.msa_chohj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("internal/member")
@RequiredArgsConstructor
public class MemberInternalController {

    private final MemberService memberService;

    @GetMapping("/name/{memberId}")
    public ResponseEntity<?> getMemberNameById(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(memberService.findNameById(memberId));
    }

    @PostMapping("/nameList")
    public ResponseEntity<?> getAllMemberNameById(@RequestBody MemberNameListRequestDTO dto) {
        System.out.println(dto.toString());
        return ResponseEntity.ok(memberService.findAllNameById(dto));
    }
}
