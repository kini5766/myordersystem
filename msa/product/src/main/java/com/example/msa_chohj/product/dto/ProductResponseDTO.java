package com.example.msa_chohj.product.dto;

import com.example.msa_chohj.product.domain.Product;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private Long memberId;
    private String memberName;

    public static ProductResponseDTO fromEntity(Product product, String memberName) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .memberId(product.getMemberId())
                .memberName(memberName)
                .build();
    }
}
