package com.example.msa_chohj.member.service;

import com.example.msa_chohj.member.domain.Member;
import com.example.msa_chohj.member.dto.*;
import com.example.msa_chohj.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long save(MemberSaveRequestDTO memberSaveRequestDTO) {
        Optional<Member> optionalMember = memberRepository.findByEmail(memberSaveRequestDTO.getEmail());
        if (optionalMember.isPresent()) {
            throw new IllegalStateException("기존에 존재하는 회원입니다.");
        }
        String password = passwordEncoder.encode(memberSaveRequestDTO.getPassword());
        Member member = memberRepository.save(memberSaveRequestDTO.toEntity(password));
        return member.getId();
    }

    public Boolean existsUser(String email) {
        return memberRepository.existsByEmail(email);
    }

    public MemberResponseDTO readMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new BadCredentialsException("invalid refresh token"));
        return new MemberResponseDTO(member.getEmail(), member.getName());
    }

    public MemberNameDTO findNameById(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        return optionalMember.map(member -> new MemberNameDTO(memberId, member.getName())).orElseGet(() -> new MemberNameDTO(memberId, null));
    }

    public List<MemberNameDTO> findAllNameById(MemberNameListRequestDTO dto) {
        if (dto.idList() == null || dto.idList().isEmpty()) {
            return Collections.emptyList();
        }
        List<Member> list = memberRepository.findAllById(dto.idList());
        return list
                .stream()
                .map(member -> new MemberNameDTO(member.getId(), member.getName()))
                .toList();
    }
}
