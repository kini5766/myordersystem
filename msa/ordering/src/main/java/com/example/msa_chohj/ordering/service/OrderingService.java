package com.example.msa_chohj.ordering.service;

import com.example.msa_chohj.ordering.domain.Ordering;
import com.example.msa_chohj.ordering.dto.*;
import com.example.msa_chohj.ordering.repository.OrderingRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderingService {
    private final OrderingRepository orderingRepository;
    private final MemberFeign memberFeign;
    private final ProductFeign productFeign;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Long orderCreate(OrderCreateDTO orderDto, Long memberId) {
        ProductDTO product = productFeign.productDetailApi(orderDto.getProductId());
        if (product == null) {
            throw new EntityNotFoundException("product is not found");
        }
        int quantity = orderDto.getProductCount();
        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("재고 부족");
        } else {
            ProductUpdateStockDTO updateDTO = ProductUpdateStockDTO
                    .builder()
                    .productId(orderDto.getProductId())
                    .stockQuantity(quantity)
                    .build();
            System.out.println("update-stock-topic: " + updateDTO.toString());
            kafkaTemplate.send("update-stock-topic", updateDTO);
        }
        Ordering ordering = Ordering.builder()
                .memberId(memberId)
                .productId(orderDto.getProductId())
                .quantity(quantity)
                .payment(product.getPrice() * quantity)
                .build();
        orderingRepository.save(ordering);
        return ordering.getId();
    }

    public List<OrderingResponseDTO> myOrderingList(Long memberId) {
        List<Ordering> orderingList = orderingRepository.findByMemberId(memberId);
        if (orderingList.isEmpty()) {
            return new ArrayList<>();
        }
        MemberNameDTO memberNameDTO = getMemberNameById(memberId);
        String ordererName = memberNameDTO.name();
        ProductListRequestDTO requestDTO = new ProductListRequestDTO(
                orderingList.stream().map(Ordering::getProductId).toList());
        List<ProductDTO> productList = getAllProductById(requestDTO);
        Map<Long, ProductDTO> productMap = new HashMap<>();
        for (ProductDTO productDTO : productList) {
            productMap.put(productDTO.getId(), productDTO);
        }
        return orderingList.stream().map(ordering -> {
            ProductDTO product = productMap.get(ordering.getProductId());
            return OrderingResponseDTO.builder()
                    .id(ordering.getId())
                    .quantity(ordering.getQuantity())
                    .orderStatus(ordering.getOrderStatus())
                    .ordererName(ordererName)
                    .productId(product.getId())
                    .productName(product.getName())
                    .sellerName(product.getMemberName())
                    .payment(ordering.getPayment())
                    .build();
        }).toList();
    }

    private MemberNameDTO getMemberNameById(Long memberId) {
        return memberFeign.getMemberNameById(memberId);
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackProductService")
    public List<ProductDTO> getAllProductById(ProductListRequestDTO dto) {
        return productFeign.getAllProductById(dto);
    }

    public List<ProductDTO> fallbackProductService(ProductListRequestDTO dto, Throwable cause) {
        throw new RuntimeException("상품 서비스가 응답이 없어, 에러가 발생했습니다. 나중에 다시 해주세요.");
    }
}
