package com.pio.raonback.entity;

import com.pio.raonback.entity.enums.ProductStatus;
import com.pio.raonback.entity.enums.TradeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product_detail_view")
@Table(name = "product_detail_view")
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
  private ProductStatus status;
  private TradeType tradeType;
  private boolean isSold;
  private boolean isActive;
  private boolean isDeleted;
  private String createdAt;
  private String updatedAt;
  private String deletedAt;

}
