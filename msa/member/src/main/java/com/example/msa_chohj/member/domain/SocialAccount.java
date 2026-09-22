package com.example.msa_chohj.member.domain;

import com.example.msa_chohj.common.domain.BaseTimeEntity;
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
public class SocialAccount extends BaseTimeEntity {
    @Id
    private String id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialProviderType providerType;

    private String email;

    @Column(nullable = false)
    private Long memberId;
}
