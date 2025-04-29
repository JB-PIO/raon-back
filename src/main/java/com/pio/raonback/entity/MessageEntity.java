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
  private boolean isRead;
  private boolean isDeleted;
  private String sentAt;
  private String deletedAt;

}
