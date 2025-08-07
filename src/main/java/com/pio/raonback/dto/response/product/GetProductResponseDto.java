package com.pio.raonback.dto.response.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Category;
import com.pio.raonback.entity.Location;
import com.pio.raonback.entity.Product;
import com.pio.raonback.entity.User;
import com.pio.raonback.entity.enums.Condition;
import com.pio.raonback.entity.enums.TradeType;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class GetProductResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private Long productId;
    private UserData seller;
    private List<CategoryData> categoryList;
    private LocationData location;
    private List<String> imageUrlList;
    private String title;
    private String description;
    private Long price;
    private Long viewCount;
    private Long favoriteCount;
    private Condition condition;
    private TradeType tradeType;
    private Boolean isSold;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

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
    private static class CategoryData {

      private Long categoryId;
      private Long level;
      private String name;

      private CategoryData(Category category) {
        this.categoryId = category.getCategoryId();
        this.level = category.getLevel();
        this.name = category.getName();
      }

      private static List<CategoryData> toParentList(Category category) {
        List<CategoryData> list = new ArrayList<>();
        while (category != null) {
          list.add(new CategoryData(category));
          category = category.getParent();
        }
        Collections.reverse(list);
        return list;
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

    private Data(Product product) {
      this.productId = product.getProductId();
      this.seller = new UserData(product.getSeller());
      this.categoryList = CategoryData.toParentList(product.getCategory());
      this.location = new LocationData(product.getLocation());
      this.imageUrlList = product.getImages().stream().map(productImage -> productImage.getImageUrl()).toList();
      this.title = product.getTitle();
      this.description = product.getDescription();
      this.price = product.getPrice();
      this.viewCount = product.getViewCount();
      this.favoriteCount = (long) product.getFavorites().size();
      this.condition = product.getCondition();
      this.tradeType = product.getTradeType();
      this.isSold = product.getIsSold();
      this.createdAt = product.getCreatedAt();
      this.updatedAt = product.getUpdatedAt();
    }

  }

  private GetProductResponseDto(Product product) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(product);
  }

  public static ResponseEntity<GetProductResponseDto> ok(Product product) {
    GetProductResponseDto responseBody = new GetProductResponseDto(product);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
