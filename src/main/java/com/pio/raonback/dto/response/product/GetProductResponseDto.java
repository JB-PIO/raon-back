package com.pio.raonback.dto.response.product;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.ProductDetailViewEntity;
import com.pio.raonback.entity.ProductImageEntity;
import com.pio.raonback.entity.enums.ProductStatus;
import com.pio.raonback.entity.enums.TradeType;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetProductResponseDto extends ResponseDto {

  private Long productId;
  private Long sellerId;
  private String sellerNickname;
  private String sellerProfileImage;
  private Long categoryId;
  private Long locationId;
  private String address;
  private List<String> imageUrlList;
  private String title;
  private String description;
  private Long price;
  private Long viewCount;
  private Long favoriteCount;
  private ProductStatus status;
  private TradeType tradeType;
  private Boolean isSold;
  private String createdAt;
  private String updatedAt;

  private GetProductResponseDto(ProductDetailViewEntity productDetailViewEntity, List<ProductImageEntity> productImageEntities) {
    super(ResponseCode.OK, ResponseMessage.OK);

    List<String> imageUrls = new ArrayList<>();
    for (ProductImageEntity productImageEntity : productImageEntities) {
      String imageUrl = productImageEntity.getImageUrl();
      imageUrls.add(imageUrl);
    }

    this.productId = productDetailViewEntity.getProductId();
    this.sellerId = productDetailViewEntity.getSellerId();
    this.sellerNickname = productDetailViewEntity.getSellerNickname();
    this.sellerProfileImage = productDetailViewEntity.getSellerProfileImage();
    this.categoryId = productDetailViewEntity.getCategoryId();
    this.locationId = productDetailViewEntity.getLocationId();
    this.address = productDetailViewEntity.getAddress();
    this.imageUrlList = imageUrls;
    this.title = productDetailViewEntity.getTitle();
    this.description = productDetailViewEntity.getDescription();
    this.price = productDetailViewEntity.getPrice();
    this.viewCount = productDetailViewEntity.getViewCount();
    this.favoriteCount = productDetailViewEntity.getFavoriteCount();
    this.status = productDetailViewEntity.getStatus();
    this.tradeType = productDetailViewEntity.getTradeType();
    this.isSold = productDetailViewEntity.getIsSold();
    this.createdAt = productDetailViewEntity.getCreatedAt();
    this.updatedAt = productDetailViewEntity.getUpdatedAt();
  }

  public static ResponseEntity<GetProductResponseDto> ok(ProductDetailViewEntity productDetailViewEntity, List<ProductImageEntity> productImageEntities) {
    GetProductResponseDto responseBody = new GetProductResponseDto(productDetailViewEntity, productImageEntities);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> productNotFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.PRODUCT_NOT_FOUND, ResponseMessage.PRODUCT_NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

}
