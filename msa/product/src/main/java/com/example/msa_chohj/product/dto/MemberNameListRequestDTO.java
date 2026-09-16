package com.example.msa_chohj.product.dto;

import java.util.List;

public record MemberNameListRequestDTO(
        List<Long> idList
) {
}
