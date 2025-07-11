package com.pio.raonback.entity;

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
@Entity(name = "refresh_token")
@Table(name = "refresh_token")
public class RefreshTokenEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long tokenId;
  private String email;
  private String token;
  private String createdAt;
  private String expiresAt;

  public RefreshTokenEntity(String email, String tokenHash, Date expiration) {
    Date now = Date.from(Instant.now());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String createdAt = simpleDateFormat.format(now);
    String expiresAt = simpleDateFormat.format(expiration);

    this.email = email;
    this.token = tokenHash;
    this.createdAt = createdAt;
    this.expiresAt = expiresAt;
  }

}
