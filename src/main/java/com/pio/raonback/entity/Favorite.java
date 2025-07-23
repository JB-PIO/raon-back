package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "favorite")
@Table(name = "favorite", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}))
public class Favorite {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "favorite_id", nullable = false)
  private Long favoriteId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  public Favorite(User user, Product product) {
    this.user = user;
    this.product = product;
    this.createdAt = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Favorite)) return false;
    final Favorite favorite = (Favorite) obj;
    return favorite.getFavoriteId().equals(getFavoriteId());
  }

  @Override
  public int hashCode() {
    return getFavoriteId().hashCode();
  }

}
