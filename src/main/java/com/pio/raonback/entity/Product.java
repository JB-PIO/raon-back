package com.pio.raonback.entity;

import com.pio.raonback.entity.enums.Condition;
import com.pio.raonback.entity.enums.ProductStatus;
import com.pio.raonback.entity.enums.TradeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product")
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id", nullable = false)
  private Long productId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seller_id", nullable = false)
  private User seller;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id", nullable = false)
  private Location location;

  @Column(name = "title", nullable = false, length = 100)
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "price", nullable = false)
  private Long price;

  @Column(name = "view_count", nullable = false)
  private Long viewCount = 0L;

  @Enumerated(EnumType.STRING)
  @Column(name = "`condition`", nullable = false)
  private Condition condition;

  @Enumerated(EnumType.STRING)
  @Column(name = "trade_type", nullable = false)
  private TradeType tradeType;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private ProductStatus status = ProductStatus.AVAILABLE;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = false;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Favorite> favorites = new ArrayList<>();

  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProductImage> images = new ArrayList<>();

  public Product(User seller, Category category, Location location, String title, String description, Long price, Condition condition, TradeType tradeType) {
    this.seller = seller;
    this.category = category;
    this.location = location;
    this.title = title;
    this.description = description;
    this.price = price;
    this.condition = condition;
    this.tradeType = tradeType;
    this.createdAt = LocalDateTime.now();
  }

  public void update(Category category, Location location, String title, String description, Long price, Condition condition, TradeType tradeType) {
    this.category = category;
    this.location = location;
    this.title = title;
    this.description = description;
    this.price = price;
    this.condition = condition;
    this.tradeType = tradeType;
    this.updatedAt = LocalDateTime.now();
  }

  public void updateStatus(ProductStatus status) {
    this.status = status;
  }

  public void delete() {
    this.isActive = false;
    this.isDeleted = true;
    this.deletedAt = LocalDateTime.now();
  }

  public void increaseViewCount() {
    this.viewCount++;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Product)) return false;
    final Product product = (Product) obj;
    return product.getProductId().equals(getProductId());
  }

  @Override
  public int hashCode() {
    return getProductId().hashCode();
  }

}
