package com.pio.raonback.entity;

import com.pio.raonback.common.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

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

  @Column(name = "email", unique = true, length = 80)
  private String email;

  @Column(name = "password", length = 255)
  private String password;

  @Column(name = "profile_image", length = 255)
  private String profileImage;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private Location location;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Role role = Role.USER;

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = false;

  @Column(name = "created_at", nullable = false)
  private ZonedDateTime createdAt;

  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;

  @Column(name = "deleted_at")
  private ZonedDateTime deletedAt;

  public User(String nickname, String email, String password, Location location) {
    this.nickname = nickname;
    this.email = email;
    this.password = password;
    this.location = location;
    this.createdAt = ZonedDateTime.now();
  }

  public void updateNickname(String nickname) {
    this.nickname = nickname;
    this.updatedAt = ZonedDateTime.now();
  }

  public void updateProfileImage(String profileImage) {
    this.profileImage = profileImage;
    this.updatedAt = ZonedDateTime.now();
  }

  public void updateLocation(Location location) {
    this.location = location;
    this.updatedAt = ZonedDateTime.now();
  }

  public void delete() {
    this.email = null;
    this.password = null;
    this.profileImage = null;
    this.location = null;
    this.isDeleted = true;
    this.deletedAt = ZonedDateTime.now();
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
