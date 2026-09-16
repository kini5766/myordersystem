package com.example.msa_chohj.security.entity;

import com.example.msa_chohj.member.domain.Member;
import com.example.msa_chohj.member.domain.SocialAccount;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Long memberId;

    public CustomOAuth2User(Map<String, Object> attributes, Member member, SocialAccount social) {
        this.attributes = attributes;
        this.authorities = CustomUserDetails.toAuthorities(member.getRoles());
        this.memberId = member.getId();
    }

    @Override
    public @NonNull Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @NonNull String getName() {
        return memberId.toString();
    }
}
