package com.pio.raonback.repository;

import com.pio.raonback.entity.Product;
import com.pio.raonback.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

  void deleteAllByProduct(Product product);

}
