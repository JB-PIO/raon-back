package com.pio.raonback.entity;

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
@Entity(name = "chat")
@Table(name = "chat")
public class ChatEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long chatId;
  private Long productId;
  private Long buyerId;
  private Long sellerId;
  private String createdAt;
  private String lastMessageAt;

  public ChatEntity(Long productId, Long buyerId, Long sellerId) {
    Date now = Date.from(Instant.now());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String createdAt = simpleDateFormat.format(now);

    this.productId = productId;
    this.buyerId = buyerId;
    this.sellerId = sellerId;
    this.createdAt = createdAt;
  }

}
