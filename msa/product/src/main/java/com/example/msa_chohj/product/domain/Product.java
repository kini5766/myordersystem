package com.example.msa_chohj.product.domain;

import com.example.msa_chohj.common.domain.BaseTimeEntity;
import com.example.msa_chohj.product.dto.ProductRegisterDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer price;
    private Integer stockQuantity;
    @Column(nullable = false)
    @Builder.Default
    private boolean isLock = false;
    @Column(nullable = false)
    private Long memberId;

    public void updateStockQuantity(Integer stockQuantity) {
        this.stockQuantity = this.stockQuantity - stockQuantity;
    }

    public void updateProduct(ProductRegisterDTO dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.stockQuantity = dto.getStockQuantity();
    }

    public void updateLockStatus(boolean isLock) {
        this.isLock = isLock;
    }
}
