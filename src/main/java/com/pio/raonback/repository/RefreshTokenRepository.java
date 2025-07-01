package com.pio.raonback.repository;

import com.pio.raonback.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

  Optional<RefreshTokenEntity> findByToken(String tokenHash);

  void deleteByEmail(String email);

  @Transactional
  void deleteByToken(String tokenHash);

}
