package com.pio.raonback.entity;

import com.pio.raonback.entity.enums.ProductStatus;
import com.pio.raonback.entity.enums.TradeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product")
@Table(name = "product")
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;
  private Long userId;
  private Long categoryId;
  private Long locationId;
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
