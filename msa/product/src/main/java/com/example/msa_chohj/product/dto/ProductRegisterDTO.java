package com.example.msa_chohj.product.dto;

import com.example.msa_chohj.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRegisterDTO {
    // 제품명
    private String name;
    // 제품 가격
    private int price;
    // 제품 재고
    private int stockQuantity;

    public Product toEntity(Long memberId) {
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .stockQuantity(this.stockQuantity)
                .memberId(memberId)
                .build();
    }
}
