package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "message")
@Table(name = "message")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "message_id", nullable = false)
  private Long messageId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_id", nullable = false)
  private Chat chat;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sender_id", nullable = false)
  private User sender;

  @Column(name = "content")
  private String content;

  @Column(name = "image_url", length = 255)
  private String imageUrl;

  @Column(name = "is_read", nullable = false)
  private Boolean isRead = false;

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = false;

  @Column(name = "sent_at", nullable = false)
  private LocalDateTime sentAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  public Message(Chat chat, User sender, String content, String imageUrl) {
    this.chat = chat;
    this.sender = sender;
    this.content = content;
    this.imageUrl = imageUrl;
    this.sentAt = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Message)) return false;
    final Message message = (Message) obj;
    return message.getMessageId().equals(getMessageId());
  }

  @Override
  public int hashCode() {
    return getMessageId().hashCode();
  }

}
