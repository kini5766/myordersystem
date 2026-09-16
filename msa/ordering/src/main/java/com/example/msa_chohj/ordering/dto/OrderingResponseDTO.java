package com.example.msa_chohj.ordering.dto;

import com.example.msa_chohj.ordering.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderingResponseDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String sellerName;
    private String ordererName;
    private Integer quantity;
    private Integer payment;
    private OrderStatus orderStatus;
}
