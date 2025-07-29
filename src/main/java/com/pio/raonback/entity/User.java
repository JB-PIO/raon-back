package com.pio.raonback.entity;

import com.pio.raonback.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "nickname", nullable = false, unique = true, length = 30)
  private String nickname;

  @Column(name = "email", nullable = false, unique = true, length = 80)
  private String email;

  @Column(name = "password", length = 255)
  private String password;

  @Column(name = "profile_image", length = 255)
  private String profileImage;

  @ManyToOne
  @JoinColumn(name = "location_id", nullable = false)
  private Location location;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Role role = Role.USER;

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = false;

  @Column(name = "is_suspended", nullable = false)
  private Boolean isSuspended = false;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "suspended_at")
  private LocalDateTime suspendedAt;

  public User(String nickname, String email, String password, Location location) {
    this.nickname = nickname;
    this.email = email;
    this.password = password;
    this.location = location;
    this.createdAt = LocalDateTime.now();
  }

  public void updateNickname(String nickname) {
    this.nickname = nickname;
    this.updatedAt = LocalDateTime.now();
  }

  public void updateProfileImage(String profileImage) {
    this.profileImage = profileImage;
    this.updatedAt = LocalDateTime.now();
  }

  public void updateLocation(Location location) {
    this.location = location;
    this.updatedAt = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof User)) return false;
    final User user = (User) obj;
    return user.getUserId().equals(getUserId());
  }

  @Override
  public int hashCode() {
    return getUserId().hashCode();
  }

}
