package com.example.msa_chohj.member.service;

import com.example.msa_chohj.member.dto.SocialDTO;
import com.example.msa_chohj.member.repository.SocialAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialAccountService {

    private final SocialAccountRepository socialAccountRepository;

    public Boolean existsByEmail(String email) {
        return socialAccountRepository.existsByEmail(email);
    }

    public List<SocialDTO> mySocialList(Long memberId) {
        return socialAccountRepository.findByMemberId(memberId)
                .stream()
                .map(social -> new SocialDTO(social.getProviderType()))
                .toList();
    }
}
