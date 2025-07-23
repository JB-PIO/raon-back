package com.pio.raonback.repository;

import com.pio.raonback.entity.Favorite;
import com.pio.raonback.entity.Product;
import com.pio.raonback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

  Optional<Favorite> findByUserAndProduct(User user, Product product);

}
