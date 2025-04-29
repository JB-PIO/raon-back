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
public class ProductImageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long imageId;
  private Long productId;
  private Long displayOrder;
  private String imageUrl;
  private String uploadedAt;

}
