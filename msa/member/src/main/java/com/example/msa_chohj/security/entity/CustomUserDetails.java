package com.example.msa_chohj.security.entity;

import com.example.msa_chohj.member.domain.Member;
import com.example.msa_chohj.member.domain.Role;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Long id;
    @Getter
    private final String email;
    private final String password;
    private final boolean enabled;
    private final boolean accountNonLocked;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(Member entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.enabled = entity.isEnabled();
        this.accountNonLocked = entity.isAccountNonLocked();
        this.authorities = toAuthorities(entity.getRoles());
    }

    public static List<GrantedAuthority> toAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(Role.toAuthorityName(role)))
                .toList();
    }

    @Override
    public @NonNull String getUsername() {
        return id.toString();
    }

    // 소셜만 가입한 경우 null 가능
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}