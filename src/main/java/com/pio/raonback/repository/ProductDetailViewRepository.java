package com.pio.raonback.repository;

import com.pio.raonback.entity.ProductDetailViewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailViewRepository extends JpaRepository<ProductDetailViewEntity, Long> {

  Page<ProductDetailViewEntity> findAllByIsSoldFalseAndIsActiveTrueOrderByCreatedAtDesc(Pageable pageable);

}
