package com.pio.raonback.dto.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.entity.Location;
import com.pio.raonback.entity.ProductDetail;
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

  public ProductListItem(ProductDetail productDetail) {
    this.productId = productDetail.getProductId();
    this.seller = new UserData(productDetail.getSeller());
    this.location = new LocationData(productDetail.getLocation());
    this.thumbnail = productDetail.getThumbnail();
    this.title = productDetail.getTitle();
    this.price = productDetail.getPrice();
    this.viewCount = productDetail.getViewCount();
    this.favoriteCount = productDetail.getFavoriteCount();
    this.status = productDetail.getStatus();
    this.tradeType = productDetail.getTradeType();
    this.isSold = productDetail.getIsSold();
    this.createdAt = productDetail.getCreatedAt();
  }

  public static List<ProductListItem> copyList(Page<ProductDetail> productDetailPage) {
    List<ProductListItem> list = new ArrayList<>();
    for (ProductDetail productDetail : productDetailPage.getContent()) {
      ProductListItem productListItem = new ProductListItem(productDetail);
      list.add(productListItem);
    }
    return list;
  }

}
