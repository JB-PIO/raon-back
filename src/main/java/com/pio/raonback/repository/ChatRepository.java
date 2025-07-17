package com.pio.raonback.repository;

import com.pio.raonback.entity.ChatEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

  boolean existsByProductIdAndBuyerIdAndSellerId(Long productId, Long buyerId, Long sellerId);

  Page<ChatEntity> findAllByBuyerIdOrSellerIdOrderByLastMessageAtDesc(Long buyerId, Long sellerId, Pageable pageable);

}
