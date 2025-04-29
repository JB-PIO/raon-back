package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
