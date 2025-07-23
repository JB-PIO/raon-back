package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "refresh_token")
@Table(name = "refresh_token")
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "token_id", nullable = false)
  private Long tokenId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private User user;

  @Column(name = "token", nullable = false, unique = true, length = 128)
  private String token;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "expires_at", nullable = false)
  private LocalDateTime expiresAt;

  public RefreshToken(User user, String tokenHash, LocalDateTime expiresAt) {
    this.user = user;
    this.token = tokenHash;
    this.createdAt = LocalDateTime.now();
    this.expiresAt = expiresAt;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof RefreshToken)) return false;
    final RefreshToken refreshToken = (RefreshToken) obj;
    return refreshToken.getTokenId().equals(getTokenId());
  }

  @Override
  public int hashCode() {
    return getTokenId().hashCode();
  }

}
