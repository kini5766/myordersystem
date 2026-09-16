package com.example.msa_chohj.ordering.dto;

import java.util.List;

public record ProductListRequestDTO(
        List<Long> idList
) {
}
