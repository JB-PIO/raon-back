package com.pio.raonback.entity;

import com.pio.raonback.entity.enums.Condition;
import com.pio.raonback.entity.enums.TradeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product_detail")
@Table(name = "product_detail")
@Immutable
public class ProductDetail {

  @Id
  private Long productId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seller_id")
  private User seller;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id")
  private Location location;

  private String thumbnail;

  private String title;

  private String description;

  private Long price;

  private Long viewCount;

  private Long favoriteCount;

  @Enumerated(EnumType.STRING)
  @Column(name = "`condition`")
  private Condition condition;

  @Enumerated(EnumType.STRING)
  private TradeType tradeType;

  private Boolean isSold;

  private Boolean isActive;

  private Boolean isDeleted;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private LocalDateTime deletedAt;

}
