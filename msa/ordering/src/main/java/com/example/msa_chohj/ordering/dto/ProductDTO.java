package com.example.msa_chohj.ordering.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String memberName;
}
