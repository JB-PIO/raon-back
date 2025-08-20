package com.pio.raonback.repository;

import com.pio.raonback.entity.ProductDetail;
import com.pio.raonback.entity.User;
import com.pio.raonback.repository.querydsl.QProductDetailRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long>, QProductDetailRepository {

  Page<ProductDetail> findAllBySellerAndIsDeletedFalse(User seller, Pageable pageable);

}
