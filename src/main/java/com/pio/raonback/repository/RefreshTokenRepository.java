package com.pio.raonback.repository;

import com.pio.raonback.entity.RefreshToken;
import com.pio.raonback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByToken(String tokenHash);

  @Transactional
  void deleteByUser(User user);

  @Transactional
  void deleteByToken(String tokenHash);

  @Transactional
  void deleteAllByExpiresAtBefore(ZonedDateTime expiresAt);

}
