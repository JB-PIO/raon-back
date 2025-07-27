package com.pio.raonback.dto.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.entity.Location;
import com.pio.raonback.entity.Product;
import com.pio.raonback.entity.User;
import com.pio.raonback.entity.enums.ProductStatus;
import com.pio.raonback.entity.enums.TradeType;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductListItem {

  private Long productId;
  private UserData seller;
  private LocationData location;
  private String thumbnail;
  private String title;
  private Long price;
  private Long viewCount;
  private Long favoriteCount;
  private ProductStatus status;
  private TradeType tradeType;
  private Boolean isSold;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @Getter
  private static class UserData {

    private Long userId;
    private String nickname;
    private String profileImage;

    private UserData(User user) {
      this.userId = user.getUserId();
      this.nickname = user.getNickname();
      this.profileImage = user.getProfileImage();
    }

  }

  @Getter
  private static class LocationData {

    private Long locationId;
    private String address;

    private LocationData(Location location) {
      this.locationId = location.getLocationId();
      this.address = location.getAddress();
    }

  }

  public ProductListItem(Product product) {
    this.productId = product.getProductId();
    this.seller = new UserData(product.getSeller());
    this.location = new LocationData(product.getLocation());
    this.thumbnail = product.getImages().get(0).getImageUrl();
    this.title = product.getTitle();
    this.price = product.getPrice();
    this.viewCount = product.getViewCount();
    this.favoriteCount = (long) product.getFavorites().size();
    this.status = product.getStatus();
    this.tradeType = product.getTradeType();
    this.isSold = product.getIsSold();
    this.createdAt = product.getCreatedAt();
  }

  public static List<ProductListItem> copyList(Page<Product> productPage) {
    List<ProductListItem> list = new ArrayList<>();
    for (Product product : productPage.getContent()) {
      ProductListItem productListItem = new ProductListItem(product);
      list.add(productListItem);
    }
    return list;
  }

}
