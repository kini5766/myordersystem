package com.example.msa_chohj.ordering.service;

import com.example.msa_chohj.ordering.dto.MemberNameDTO;
import com.example.msa_chohj.ordering.dto.MemberNameListRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "member-service")
public interface MemberFeign {
    @GetMapping("/member/name/{memberId}")
    MemberNameDTO getMemberNameById(@PathVariable("memberId") Long memberId);

    @PostMapping("/member/nameList")
    List<MemberNameDTO> getAllMemberNameById(@RequestBody MemberNameListRequestDTO dto);
}
