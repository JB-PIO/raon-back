package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "block")
@Table(name = "block")
public class Block {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "block_id", nullable = false)
  private Long blockId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "blocked_user_id", nullable = false)
  private User blockedUser;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Block)) return false;
    final Block block = (Block) obj;
    return block.getBlockId().equals(getBlockId());
  }

  @Override
  public int hashCode() {
    return getBlockId().hashCode();
  }

}
