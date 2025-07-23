package com.pio.raonback.repository;

import com.pio.raonback.entity.Location;
import com.pio.raonback.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByIsActiveTrueAndProductId(Long productId);

  Page<Product> findAllByIsSoldFalseAndIsActiveTrueOrderByCreatedAtDesc(Pageable pageable);

  Page<Product> findAllByIsSoldFalseAndIsActiveTrueAndLocationInOrderByCreatedAtDesc(Collection<Location> locations, Pageable pageable);

}
