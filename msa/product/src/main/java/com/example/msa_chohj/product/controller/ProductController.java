package com.example.msa_chohj.product.controller;

import com.example.msa_chohj.product.dto.ProductRegisterDTO;
import com.example.msa_chohj.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> productCreateApi(@RequestBody ProductRegisterDTO dto, @RequestHeader("X-User-Id") String memberId) {
        return new ResponseEntity<>(productService.productCreate(dto, Long.parseLong(memberId)), HttpStatus.CREATED);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> productDetailApi(@PathVariable Long id) {
        return new ResponseEntity<>(productService.productDetail(id), HttpStatus.OK);
    }

    @PutMapping("/modifiy/{id}")
    public ResponseEntity<?> productModifyApi(@PathVariable Long id, @RequestBody ProductRegisterDTO dto) {
        return new ResponseEntity<>(productService.productModify(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> productDeleteApi(@PathVariable Long id) {
        productService.productDelete(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getProductListApi() {
        return new ResponseEntity<>(productService.productList(), HttpStatus.OK);
    }

    @GetMapping("/myList")
    public ResponseEntity<?> getMyProductListApi(@RequestHeader("X-User-Id") String memberId) {
        return new ResponseEntity<>(productService.myProductList(Long.parseLong(memberId)), HttpStatus.OK);
    }

    @GetMapping("/search/{text}")
    public ResponseEntity<?> getProductListApi(@PathVariable String text) {
        return new ResponseEntity<>(productService.searchList(text), HttpStatus.OK);
    }
}
