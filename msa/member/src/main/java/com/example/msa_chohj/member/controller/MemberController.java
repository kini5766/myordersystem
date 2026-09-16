package com.example.msa_chohj.member.controller;

import com.example.msa_chohj.member.dto.*;
import com.example.msa_chohj.security.service.CustomOAuth2UserService;
import com.example.msa_chohj.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final CustomOAuth2UserService oAuth2UserService;

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
        return ResponseEntity.ok(oAuth2UserService.existsByEmail(email));
    }

    @GetMapping("/info")
    public ResponseEntity<?> memberInfoApi() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(memberService.readMember(Long.parseLong(id)));
    }

    @GetMapping("/mySocialList")
    public ResponseEntity<?> mySocialListApi() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(oAuth2UserService.mySocialList(Long.parseLong(id)));
    }

    // -- service to service --

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
