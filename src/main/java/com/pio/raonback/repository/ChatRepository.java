package com.pio.raonback.repository;

import com.pio.raonback.entity.Chat;
import com.pio.raonback.entity.Product;
import com.pio.raonback.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

  boolean existsByProductAndBuyerAndSeller(Product product, User buyer, User seller);

  boolean existsByProductAndBuyerAndLastMessageAtNotNull(Product product, User buyer);

  Page<Chat> findAllByBuyerOrSellerAndLastMessageAtNotNull(User buyer, User seller, Pageable pageable);

  Page<Chat> findAllByProductAndBuyerIsDeletedFalseAndBuyerIsSuspendedFalseAndLastMessageAtNotNull(Product product, Pageable pageable);

  Optional<Chat> findByProductAndBuyerAndSeller(Product product, User buyer, User seller);

}
