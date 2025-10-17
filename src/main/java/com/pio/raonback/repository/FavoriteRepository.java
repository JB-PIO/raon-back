package com.pio.raonback.repository;

import com.pio.raonback.entity.Favorite;
import com.pio.raonback.entity.Product;
import com.pio.raonback.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

  boolean existsByUserAndProduct(User user, Product product);

  Optional<Favorite> findByUserAndProduct(User user, Product product);

  Page<Favorite> findAllByUserAndProductIsDeletedFalse(User user, Pageable pageable);

  void deleteAllByUser(User user);

  void deleteAllByProduct(Product product);

}
