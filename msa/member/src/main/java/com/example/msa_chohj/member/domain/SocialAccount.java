package com.example.msa_chohj.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SocialAccount {
    @Id
    private String id;

    @Column(nullable = false)
    private SocialProviderType providerType;

    private String email;

    @Column(nullable = false)
    private Long memberId;
}
