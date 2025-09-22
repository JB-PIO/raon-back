package com.pio.raonback.dto.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.common.enums.Condition;
import com.pio.raonback.common.enums.ProductStatus;
import com.pio.raonback.common.enums.TradeType;
import com.pio.raonback.entity.*;
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
  private Condition condition;
  private TradeType tradeType;
  private ProductStatus status;
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
    this.condition = product.getCondition();
    this.tradeType = product.getTradeType();
    this.status = product.getStatus();
    this.createdAt = product.getCreatedAt();
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
    this.condition = productDetail.getCondition();
    this.tradeType = productDetail.getTradeType();
    this.status = productDetail.getStatus();
    this.createdAt = productDetail.getCreatedAt();
  }

  public static List<ProductListItem> fromProductDetailPage(Page<ProductDetail> productDetailPage) {
    List<ProductListItem> list = new ArrayList<>();
    for (ProductDetail productDetail : productDetailPage.getContent()) {
      ProductListItem productListItem = new ProductListItem(productDetail);
      list.add(productListItem);
    }
    return list;
  }

  public static List<ProductListItem> fromFavoritePage(Page<Favorite> favoritePage) {
    List<ProductListItem> list = new ArrayList<>();
    for (Favorite favorite : favoritePage.getContent()) {
      ProductListItem productListItem = new ProductListItem(favorite.getProduct());
      list.add(productListItem);
    }
    return list;
  }

}
