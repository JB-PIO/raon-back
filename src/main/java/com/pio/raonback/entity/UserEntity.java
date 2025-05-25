package com.pio.raonback.entity;

import com.pio.raonback.dto.request.auth.SignUpRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(name = "user")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  private String nickname;
  private String email;
  private String password;
  private String profileImage;
  private Long locationId;
  private Boolean isDeleted = false;
  private Boolean isSuspended = false;
  private String createdAt;
  private String updatedAt;
  private String deletedAt;
  private String suspendedAt;

  public UserEntity(SignUpRequestDto dto) {
    Date now = Date.from(Instant.now());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String createdAt = simpleDateFormat.format(now);

    this.nickname = dto.getNickname();
    this.email = dto.getEmail();
    this.password = dto.getPassword();
    this.locationId = dto.getLocationId();
    this.createdAt = createdAt;
  }

}
