package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "block")
@Table(name = "block")
public class BlockEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long blockId;
  private Long userId;
  private Long blockedUserId;
  private String createdAt;

}
