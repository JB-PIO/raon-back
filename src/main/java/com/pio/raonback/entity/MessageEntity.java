package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "message")
@Table(name = "message")
public class MessageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long messageId;
  private Long chatId;
  private Long senderId;
  private String content;
  private String imageUrl;
  private Boolean isRead = false;
  private Boolean isDeleted = false;
  private String sentAt;
  private String deletedAt;

}
