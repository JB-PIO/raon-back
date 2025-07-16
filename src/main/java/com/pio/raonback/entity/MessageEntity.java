package com.pio.raonback.entity;

import com.pio.raonback.dto.request.chat.SendMessageRequestDto;
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

  public MessageEntity(Long chatId, SendMessageRequestDto dto, Long senderId) {
    Date now = Date.from(Instant.now());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String sentAt = simpleDateFormat.format(now);

    this.chatId = chatId;
    this.senderId = senderId;
    this.content = dto.getContent();
    this.imageUrl = dto.getImageUrl();
    this.sentAt = sentAt;
  }

}
