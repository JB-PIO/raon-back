package com.pio.raonback.repository;

import com.pio.raonback.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  UserEntity findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByNickname(String nickname);
  
}
