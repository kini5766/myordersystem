package com.example.msa_chohj.ordering.service;

import com.example.msa_chohj.ordering.dto.ProductDTO;
import com.example.msa_chohj.ordering.dto.ProductListRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductFeign {
    @GetMapping("/product/detail/{id}")
    ProductDTO productDetailApi(@PathVariable("id") Long id);

    @PostMapping("/product/list")
    List<ProductDTO> getAllProductById(@RequestBody ProductListRequestDTO dto);
}
