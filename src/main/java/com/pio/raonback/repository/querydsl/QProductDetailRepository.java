package com.pio.raonback.repository.querydsl;

import com.pio.raonback.common.enums.Condition;
import com.pio.raonback.common.enums.TradeType;
import com.pio.raonback.entity.Category;
import com.pio.raonback.entity.Location;
import com.pio.raonback.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QProductDetailRepository {

  Page<ProductDetail> findAllWithFilters(Category category, List<Location> locations, Long minPrice, Long maxPrice,
                                         String keyword, Condition condition, TradeType tradeType, Pageable pageable);

}
