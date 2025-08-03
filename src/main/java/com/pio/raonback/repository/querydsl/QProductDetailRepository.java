package com.pio.raonback.repository.querydsl;

import com.pio.raonback.entity.Category;
import com.pio.raonback.entity.Location;
import com.pio.raonback.entity.ProductDetail;
import com.pio.raonback.entity.enums.ProductStatus;
import com.pio.raonback.entity.enums.TradeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QProductDetailRepository {

  Page<ProductDetail> findAllWithFilters(Category category, List<Location> locations, Long minPrice, Long maxPrice,
                                         String keyword, ProductStatus status, TradeType tradeType, Pageable pageable);

}
