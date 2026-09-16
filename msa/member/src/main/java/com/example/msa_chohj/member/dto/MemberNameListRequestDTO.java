package com.example.msa_chohj.member.dto;

import java.util.List;

public record MemberNameListRequestDTO(
        List<Long> idList
) {
}
