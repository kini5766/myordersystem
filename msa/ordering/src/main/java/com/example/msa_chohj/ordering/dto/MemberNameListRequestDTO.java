package com.example.msa_chohj.ordering.dto;

import java.util.List;

public record MemberNameListRequestDTO(
        List<Long> idList
) {
}
