package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "favorite")
@Table(name = "favorite")
public class FavoriteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long favoriteId;
  private Long userId;
  private Long productId;
  private String createdAt;

}
