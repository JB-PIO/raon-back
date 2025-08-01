package com.pio.raonback.repository;

import com.pio.raonback.entity.Product;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class QProductRepositoryImpl extends QuerydslRepositorySupport implements QProductRepository {

  public QProductRepositoryImpl() {
    super(Product.class);
  }

}
