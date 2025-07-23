package com.pio.raonback.dto.response.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Product;
import com.pio.raonback.entity.enums.ProductStatus;
import com.pio.raonback.entity.enums.TradeType;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
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
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;

  private GetProductResponseDto(Product product) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.productId = product.getProductId();
    this.sellerId = product.getSeller().getUserId();
    this.sellerNickname = product.getSeller().getNickname();
    this.sellerProfileImage = product.getSeller().getProfileImage();
    this.categoryId = product.getCategory().getCategoryId();
    this.locationId = product.getLocation().getLocationId();
    this.address = product.getLocation().getAddress();
    this.imageUrlList = product.getImages().stream().map(productImage -> productImage.getImageUrl()).toList();
    this.title = product.getTitle();
    this.description = product.getDescription();
    this.price = product.getPrice();
    this.viewCount = product.getViewCount();
    this.favoriteCount = (long) product.getFavorites().size();
    this.status = product.getStatus();
    this.tradeType = product.getTradeType();
    this.isSold = product.getIsSold();
    this.createdAt = product.getCreatedAt();
    this.updatedAt = product.getUpdatedAt();
  }

  public static ResponseEntity<GetProductResponseDto> ok(Product product) {
    GetProductResponseDto responseBody = new GetProductResponseDto(product);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
