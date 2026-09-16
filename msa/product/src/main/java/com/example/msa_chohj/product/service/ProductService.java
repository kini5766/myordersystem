package com.example.msa_chohj.product.service;

import com.example.msa_chohj.product.domain.Product;
import com.example.msa_chohj.product.dto.*;
import com.example.msa_chohj.product.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberFeign memberFeign;

    public Long productCreate(ProductRegisterDTO dto, Long memberId) {
        if (dto.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0: " + dto);
        }
        if (dto.getStockQuantity() <= 0) {
            throw new IllegalArgumentException("Stock quantity must be greater than 0: " + dto);
        }

        Product product = productRepository.save(dto.toEntity(memberId));
        return product.getId();
    }

    public ProductResponseDTO productDetail(Long id) {
        Product product = productRepository.findByIdAndIsLock(id, false).orElseThrow(() ->
                new EntityNotFoundException("Product not found with id " + id));
        MemberNameDTO memberNameDTO = memberFeign.getMemberNameById(product.getMemberId());
        return ProductResponseDTO.fromEntity(product, memberNameDTO.name());
    }

    public Long productModify(Long id, ProductRegisterDTO dto) {
        Product product = productRepository.findByIdAndIsLock(id, false).orElseThrow(() ->
                new EntityNotFoundException("Product not found with id " + id));
        if (!product.getId().equals(id)) {
//            throw new BadCredentialsException("invalid refresh token");
            throw new EntityNotFoundException();
        }
        product.updateProduct(dto);
        return product.getId();
    }

    public List<ProductResponseDTO> productList() {
        return convertListDTO(productRepository.findByIsLock(false));
    }

    public List<ProductResponseDTO> myProductList(Long memberId) {
        return convertListDTO(productRepository.findByMemberIdAndIsLock(memberId, false));
    }

    public List<ProductResponseDTO> searchList(String text) {
        return convertListDTO(productRepository.search(text));
    }

    public void productDelete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Product not found with id " + id));
        if (!product.getId().equals(id)) {
//            throw new BadCredentialsException("invalid refresh token");
            throw new EntityNotFoundException();
        }
        product.updateLockStatus(true);
    }

    private List<ProductResponseDTO> convertListDTO(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return new ArrayList<>();
        }
        MemberNameListRequestDTO requestDTO = new MemberNameListRequestDTO(products.stream().map(Product::getMemberId).toList());
        List<MemberNameDTO> dtoList = memberFeign.getAllMemberNameById(requestDTO);
        if (dtoList == null || dtoList.isEmpty()) {
            return new ArrayList<>();
        }
        HashMap<Long, String> memberMap = new HashMap<>();
        for (MemberNameDTO dto : dtoList) {
            memberMap.put(dto.id(), dto.name());
        }
        List<ProductResponseDTO> list = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDTO productResponseDTO = ProductResponseDTO.fromEntity(product, memberMap.get(product.getMemberId()));
            list.add(productResponseDTO);
        }
        return list;
    }

    public Product updateStockQuantity(ProductUpdateStockDTO dto) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() ->
                new EntityNotFoundException("Product not found"));
        product.updateStockQuantity(dto.getStockQuantity());
        return product;
    }

    @KafkaListener(topics = "update-stock-topic", containerFactory = "kafkaListenerContainerFactory")
    public void stockConsumer(String message) {
        System.out.println("[PRODUCT-SERVICE] received message: " + message);
        ObjectMapper mapper = new ObjectMapper();
        ProductUpdateStockDTO dto;
        try {
            dto = mapper.readValue(message, ProductUpdateStockDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        this.updateStockQuantity(dto);
    }

    public List<ProductResponseDTO> productListByIdList(ProductListByIdListDTO dto) {
        return convertListDTO(productRepository.findAllById(dto.idList()).stream().toList());
    }
}
