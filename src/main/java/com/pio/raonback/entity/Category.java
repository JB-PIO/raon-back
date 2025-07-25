package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "category")
@Table(name = "category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id", nullable = false)
  private Long categoryId;

  @Column(name = "level", nullable = false)
  private Long level = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

  @Column(name = "name", nullable = false, length = 30)
  private String name;

  @Column(name = "is_leaf", nullable = false)
  private Boolean isLeaf = false;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Category)) return false;
    final Category category = (Category) obj;
    return category.getCategoryId().equals(getCategoryId());
  }

  @Override
  public int hashCode() {
    return getCategoryId().hashCode();
  }

}
