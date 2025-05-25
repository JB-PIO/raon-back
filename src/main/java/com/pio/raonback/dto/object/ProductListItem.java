package com.pio.raonback.dto.object;

import com.pio.raonback.entity.ProductDetailViewEntity;
import com.pio.raonback.entity.enums.ProductStatus;
import com.pio.raonback.entity.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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
  private String createdAt;

  public ProductListItem(ProductDetailViewEntity productDetailViewEntity) {
    this.productId = productDetailViewEntity.getProductId();
    this.sellerId = productDetailViewEntity.getSellerId();
    this.sellerNickname = productDetailViewEntity.getSellerNickname();
    this.sellerProfileImage = productDetailViewEntity.getSellerProfileImage();
    this.locationId = productDetailViewEntity.getLocationId();
    this.address = productDetailViewEntity.getAddress();
    this.thumbnail = productDetailViewEntity.getThumbnail();
    this.title = productDetailViewEntity.getTitle();
    this.price = productDetailViewEntity.getPrice();
    this.viewCount = productDetailViewEntity.getViewCount();
    this.favoriteCount = productDetailViewEntity.getFavoriteCount();
    this.status = productDetailViewEntity.getStatus();
    this.tradeType = productDetailViewEntity.getTradeType();
    this.isSold = productDetailViewEntity.getIsSold();
    this.createdAt = productDetailViewEntity.getCreatedAt();
  }

  public static List<ProductListItem> copyList(Page<ProductDetailViewEntity> productDetailViewEntitiesPage) {
    List<ProductListItem> list = new ArrayList<>();
    for (ProductDetailViewEntity productDetailViewEntity : productDetailViewEntitiesPage.getContent()) {
      ProductListItem productListItem = new ProductListItem(productDetailViewEntity);
      list.add(productListItem);
    }
    return list;
  }

}
