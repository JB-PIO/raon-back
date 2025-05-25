package com.pio.raonback.entity;

import com.pio.raonback.entity.enums.ProductStatus;
import com.pio.raonback.entity.enums.TradeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product_detail_view")
@Table(name = "product_detail_view")
@Immutable
public class ProductDetailViewEntity {

  @Id
  private Long productId;
  private Long sellerId;
  private String sellerNickname;
  private String sellerEmail;
  private String sellerProfileImage;
  private Long categoryId;
  private Long locationId;
  private String address;
  private String thumbnail;
  private String title;
  private String description;
  private Long price;
  private Long viewCount;
  private Long favoriteCount;
  @Enumerated(EnumType.STRING)
  private ProductStatus status;
  @Enumerated(EnumType.STRING)
  private TradeType tradeType;
  private Boolean isSold;
  private Boolean isActive;
  private Boolean isDeleted;
  private String createdAt;
  private String updatedAt;
  private String deletedAt;

}
