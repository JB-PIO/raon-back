package com.pio.raonback.entity;

import com.pio.raonback.entity.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transaction")
@Table(name = "transaction")
public class TransactionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long transactionId;
  private Long productId;
  private Long buyerId;
  private Long sellerId;
  @Enumerated(EnumType.STRING)
  private TransactionStatus status;
  private String createdAt;
  private String completedAt;

}
