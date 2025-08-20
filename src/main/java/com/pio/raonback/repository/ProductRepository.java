package com.pio.raonback.repository;

import com.pio.raonback.entity.Product;
import com.pio.raonback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByProductIdAndIsDeletedFalse(Long productId);

  List<Product> findAllBySellerAndIsDeletedFalse(User seller);

}
