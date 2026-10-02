package com.example.msa_chohj.member.controller;

import com.example.msa_chohj.member.dto.*;
import com.example.msa_chohj.member.service.SocialAccountService;
import com.example.msa_chohj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final SocialAccountService socialAccountService;

    @PostMapping("/create")
    public ResponseEntity<?> memberCreateApi(@RequestBody MemberSaveRequestDTO memberSaveRequestDTO) {
        Long memberId = memberService.save(memberSaveRequestDTO);
        return new ResponseEntity<>(memberId, HttpStatus.CREATED);
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<?> existsApi(@PathVariable String email) {
        boolean exists = memberService.existsUser(email);
        if (exists) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(socialAccountService.existsByEmail(email));
    }

    @GetMapping("/info")
    public ResponseEntity<?> memberInfoApi(@RequestHeader("X-User-Id") String memberId) {
        return ResponseEntity.ok(memberService.readMember(Long.parseLong(memberId)));
    }

    @GetMapping("/mySocialList")
    public ResponseEntity<?> mySocialListApi(@RequestHeader("X-User-Id") String memberId) {
        return ResponseEntity.ok(socialAccountService.mySocialList(Long.parseLong(memberId)));
    }
}
