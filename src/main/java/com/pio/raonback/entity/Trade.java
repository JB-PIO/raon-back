package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "trade")
@Table(name = "trade")
public class Trade {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "trade_id", nullable = false)
  private Long tradeId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false, unique = true)
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer_id")
  private User buyer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seller_id", nullable = false)
  private User seller;

  @Column(name = "traded_at", nullable = false)
  private ZonedDateTime tradedAt;

  public Trade(Product product, User buyer, User seller) {
    this.product = product;
    this.buyer = buyer;
    this.seller = seller;
    this.tradedAt = ZonedDateTime.now();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Trade)) return false;
    final Trade trade = (Trade) obj;
    return trade.getTradeId().equals(getTradeId());
  }

  @Override
  public int hashCode() {
    return getTradeId().hashCode();
  }

}
