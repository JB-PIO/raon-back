package com.pio.raonback.dto.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.entity.Product;
import com.pio.raonback.entity.Trade;
import com.pio.raonback.entity.User;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TradeListItem {

  private Long tradeId;
  private ProductData product;
  private UserData buyer;
  private UserData seller;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime tradedAt;

  @Getter
  private static class ProductData {

    private Long productId;
    private String thumbnail;
    private String title;
    private Long price;
    private Boolean isDeleted;

    private ProductData(Product product) {
      this.productId = product.getProductId();
      this.thumbnail = product.getImages().get(0).getImageUrl();
      this.title = product.getTitle();
      this.price = product.getPrice();
      this.isDeleted = product.getIsDeleted();
    }

  }

  @Getter
  private static class UserData {

    private Long userId;
    private String nickname;
    private String profileImage;
    private Boolean isDeleted;

    private UserData(User user) {
      this.userId = user.getUserId();
      this.nickname = user.getNickname();
      this.profileImage = user.getProfileImage();
      this.isDeleted = user.getIsDeleted();
    }

  }

  public TradeListItem(Trade trade) {
    this.tradeId = trade.getTradeId();
    this.product = new ProductData(trade.getProduct());
    this.buyer = trade.getBuyer() != null ? new UserData(trade.getBuyer()) : null;
    this.seller = new UserData(trade.getSeller());
    this.tradedAt = trade.getTradedAt();
  }

  public static List<TradeListItem> fromTradePage(Page<Trade> tradePage) {
    List<TradeListItem> list = new ArrayList<>();
    for (Trade trade : tradePage.getContent()) {
      TradeListItem tradeListItem = new TradeListItem(trade);
      list.add(tradeListItem);
    }
    return list;
  }

}
