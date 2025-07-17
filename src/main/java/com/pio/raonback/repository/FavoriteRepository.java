package com.pio.raonback.repository;

import com.pio.raonback.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

  Optional<FavoriteEntity> findByUserIdAndProductId(Long userId, Long productId);

}
