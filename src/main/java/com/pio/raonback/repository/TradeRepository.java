package com.pio.raonback.repository;

import com.pio.raonback.entity.Trade;
import com.pio.raonback.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

  Page<Trade> findAllByBuyer(User buyer, Pageable pageable);

  Page<Trade> findAllBySeller(User seller, Pageable pageable);

  Page<Trade> findAllByBuyerOrSeller(User buyer, User seller, Pageable pageable);

  void deleteAllByBuyerNullAndSeller(User seller);

}
