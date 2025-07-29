package com.pio.raonback.dto.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Location;
import com.pio.raonback.entity.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class GetProfileResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private Long userId;
    private String nickname;
    private String email;
    private String profileImage;
    private LocationData location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Getter
    private static class LocationData {

      private Long locationId;
      private String address;

      private LocationData(Location location) {
        this.locationId = location.getLocationId();
        this.address = location.getAddress();
      }

    }

    private Data(User user) {
      this.userId = user.getUserId();
      this.nickname = user.getNickname();
      this.email = user.getEmail();
      this.profileImage = user.getProfileImage();
      this.location = new LocationData(user.getLocation());
      this.createdAt = user.getCreatedAt();
    }

  }

  private GetProfileResponseDto(User user) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(user);
  }

  public static ResponseEntity<GetProfileResponseDto> ok(User user) {
    GetProfileResponseDto responseBody = new GetProfileResponseDto(user);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
