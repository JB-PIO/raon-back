package com.pio.raonback.entity;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.entity.enums.ProductStatus;
import com.pio.raonback.entity.enums.TradeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

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
  private Long viewCount = 0L;
  private Long favoriteCount = 0L;
  private ProductStatus status;
  private TradeType tradeType;
  private boolean isSold = false;
  private boolean isActive = true;
  private boolean isDeleted = false;
  private String createdAt;
  private String updatedAt;
  private String deletedAt;

  public ProductEntity(PostProductRequestDto dto, Long userId) {
    Date now = Date.from(Instant.now());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String createdAt = simpleDateFormat.format(now);

    this.userId = userId;
    this.categoryId = dto.getCategoryId();
    this.locationId = dto.getLocationId();
    this.title = dto.getTitle();
    this.description = dto.getDescription();
    this.price = dto.getPrice();
    this.status = dto.getStatus();
    this.tradeType = dto.getTradeType();
    this.createdAt = createdAt;
  }

}
