package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product_image")
@Table(name = "product_image")
public class ProductImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "image_id", nullable = false)
  private Long imageId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(name = "image_url", nullable = false, length = 255)
  private String imageUrl;

  public ProductImage(Product product, String imageUrl) {
    this.product = product;
    this.imageUrl = imageUrl;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof ProductImage)) return false;
    final ProductImage productImage = (ProductImage) obj;
    return productImage.getImageId().equals(getImageId());
  }

  @Override
  public int hashCode() {
    return getImageId().hashCode();
  }

}
