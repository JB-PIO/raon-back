package com.pio.raonback.dto.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.entity.Product;
import com.pio.raonback.entity.enums.ProductStatus;
import com.pio.raonback.entity.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductListItem {

  private Long productId;
  private Long sellerId;
  private String sellerNickname;
  private String sellerProfileImage;
  private Long locationId;
  private String address;
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

  public ProductListItem(Product product) {
    this.productId = product.getProductId();
    this.sellerId = product.getSeller().getUserId();
    this.sellerNickname = product.getSeller().getNickname();
    this.sellerProfileImage = product.getSeller().getProfileImage();
    this.locationId = product.getLocation().getLocationId();
    this.address = product.getLocation().getAddress();
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
