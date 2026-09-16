package com.example.msa_chohj.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductUpdateStockDTO {
    private Long productId;
    private Integer stockQuantity;
}
