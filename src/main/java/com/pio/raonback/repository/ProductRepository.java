package com.pio.raonback.repository;

import com.pio.raonback.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  Optional<ProductEntity> findByIsActiveTrueAndProductId(Long productId);

}
