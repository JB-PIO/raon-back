package com.pio.raonback.repository;

import com.pio.raonback.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {

  List<ProductImageEntity> findAllByProductId(Long productId);

  void deleteAllByProductId(Long productId);

}
