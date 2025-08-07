package com.pio.raonback.repository.querydsl.implement;

import com.pio.raonback.entity.Category;
import com.pio.raonback.entity.Location;
import com.pio.raonback.entity.ProductDetail;
import com.pio.raonback.entity.enums.Condition;
import com.pio.raonback.entity.enums.TradeType;
import com.pio.raonback.repository.querydsl.QProductDetailRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.pio.raonback.entity.QProductDetail.productDetail;


public class QProductDetailRepositoryImpl extends QuerydslRepositorySupport implements QProductDetailRepository {

  public QProductDetailRepositoryImpl() {
    super(ProductDetail.class);
  }

  @Override
  public Page<ProductDetail> findAllWithFilters(Category category, List<Location> locations, Long minPrice, Long maxPrice,
                                                String keyword, Condition condition, TradeType tradeType, Pageable pageable) {
    BooleanBuilder builder = new BooleanBuilder();

    builder.and(productDetail.isSold.eq(false))
           .and(productDetail.isActive.eq(true));

    if (category != null) builder.and(productDetail.category.path.startsWith(category.getPath()));
    if (locations != null) builder.and(productDetail.location.in(locations));
    if (minPrice != null) builder.and(productDetail.price.goe(minPrice));
    if (maxPrice != null) builder.and(productDetail.price.loe(maxPrice));
    if (keyword != null) builder.and(productDetail.title.containsIgnoreCase(keyword)
                                                        .or(productDetail.description.containsIgnoreCase(keyword))
                                                        .or(productDetail.seller.nickname.containsIgnoreCase(keyword)));
    if (condition != null) builder.and(productDetail.condition.eq(condition));
    if (tradeType != null) builder.and(productDetail.tradeType.eq(tradeType)
                                                              .or(productDetail.tradeType.eq(TradeType.BOTH)));

    JPQLQuery<ProductDetail> query = from(productDetail).where(builder);
    List<ProductDetail> results = getQuerydsl().applyPagination(pageable, query).fetch();
    return PageableExecutionUtils.getPage(results, pageable, query::fetchCount);
  }

}
