package com.example.msa_chohj.member.repository;

import com.example.msa_chohj.member.domain.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, String> {
    Boolean existsByEmail(String email);
    List<SocialAccount> findByMemberId(Long memberId);
}
