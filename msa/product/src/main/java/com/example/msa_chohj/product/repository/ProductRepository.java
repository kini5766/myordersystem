package com.example.msa_chohj.product.repository;

import com.example.msa_chohj.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndIsLock(long id, boolean isLock);
    List<Product> findByIsLock(boolean isLock);
    List<Product> findByMemberIdAndIsLock(Long id, boolean isLock);

    // https://velog.io/@kjyeon1101/Spring-JPA-%EC%BF%BC%EB%A6%AC-%EB%A7%8C%EB%93%A4%EA%B8%B0
    @Query("SELECT p FROM Product p WHERE p.isLock = false AND p.name LIKE %?1%")
    List<Product> search(String search);
}
