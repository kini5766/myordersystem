package com.example.msa_chohj.product.controller;

import com.example.msa_chohj.product.domain.Product;
import com.example.msa_chohj.product.dto.ProductListByIdListDTO;
import com.example.msa_chohj.product.dto.ProductUpdateStockDTO;
import com.example.msa_chohj.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("internal/product")
@RequiredArgsConstructor
public class ProductInternalController {

    private final ProductService productService;

    @PostMapping("/list")
    ResponseEntity<?> getAllProductById(@RequestBody ProductListByIdListDTO dto) {
        return new ResponseEntity<>(productService.productListByIdList(dto), HttpStatus.OK);
    }

    @PutMapping("/product/updatestock")
    public ResponseEntity<?> productStock(@RequestBody ProductUpdateStockDTO dto) {
        Product product = productService.updateStockQuantity(dto);
        return new ResponseEntity<>(product.getId(), HttpStatus.OK);
    }
}
