package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chat")
@Table(name = "chat", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "buyer_id", "seller_id"}))
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_id", nullable = false)
  private Long chatId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer_id", nullable = false)
  private User buyer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seller_id", nullable = false)
  private User seller;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Setter
  @Column(name = "last_message_at")
  private LocalDateTime lastMessageAt;

  @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Message> messages = new ArrayList<>();

  public Chat(Product product, User buyer, User seller) {
    this.product = product;
    this.buyer = buyer;
    this.seller = seller;
    this.createdAt = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Chat)) return false;
    final Chat chat = (Chat) obj;
    return chat.getChatId().equals(getChatId());
  }

  @Override
  public int hashCode() {
    return getChatId().hashCode();
  }

}
