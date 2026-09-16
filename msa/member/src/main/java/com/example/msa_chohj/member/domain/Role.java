package com.example.msa_chohj.member.domain;

public enum Role {
    ADMIN,
    USER;

    public static final String ROLE = "ROLE_";
    public static String toAuthorityName(Role role) {
        return ROLE + role.name();
    }
}
