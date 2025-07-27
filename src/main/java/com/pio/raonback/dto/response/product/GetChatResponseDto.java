package com.pio.raonback.dto.response.product;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Chat;
import com.pio.raonback.entity.Location;
import com.pio.raonback.entity.Product;
import com.pio.raonback.entity.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetChatResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private Long chatId;
    private ProductData product;
    private UserData buyer;
    private UserData seller;

    @Getter
    private static class ProductData {

      private Long productId;
      private LocationData location;
      private String thumbnail;
      private String title;
      private Long price;
      private Boolean isSold;
      private Boolean isActive;
      private Boolean isDeleted;

      @Getter
      private static class LocationData {

        private Long locationId;
        private String address;

        private LocationData(Location location) {
          this.locationId = location.getLocationId();
          this.address = location.getAddress();
        }

      }

      private ProductData(Product product) {
        this.productId = product.getProductId();
        this.location = new LocationData(product.getLocation());
        this.thumbnail = product.getImages().get(0).getImageUrl();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.isSold = product.getIsSold();
        this.isActive = product.getIsActive();
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

    private Data(Chat chat) {
      this.chatId = chat.getChatId();
      this.product = new ProductData(chat.getProduct());
      this.buyer = new UserData(chat.getBuyer());
      this.seller = new UserData(chat.getSeller());
    }

  }

  private GetChatResponseDto(Chat chat) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(chat);
  }

  public static ResponseEntity<GetChatResponseDto> ok(Chat chat) {
    GetChatResponseDto responseBody = new GetChatResponseDto(chat);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
