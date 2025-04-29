package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
  private boolean isDeleted;
  private boolean isSuspended;
  private String createdAt;
  private String updatedAt;
  private String deletedAt;
  private String suspendedAt;

}
