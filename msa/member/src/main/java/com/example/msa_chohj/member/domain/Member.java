package com.example.msa_chohj.member.domain;

import com.example.msa_chohj.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String name;

    // 활성화 여부 (비활성화 / 회원탈퇴)
    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = true;

    // 계정 잠금 여부
    @Column(nullable = false)
    @Builder.Default
    private boolean accountNonLocked = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "member_roles",
            joinColumns = @JoinColumn(name = "member_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
}
