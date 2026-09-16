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

    private final String sub;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(Map<String, Object> attributes, Member member, SocialAccount social) {
        this.sub = String.valueOf(member.getId());
        this.authorities = CustomUserDetails.toAuthorities(member.getRoles());
        this.attributes = attributes;
    }

    // jwt 토큰 sub
    @Override
    public @NonNull String getName() {
        return sub;
    }

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @NonNull Map<String, Object> getAttributes() {
        return attributes;
    }
}
