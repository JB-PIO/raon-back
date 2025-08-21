package com.pio.raonback.scheduler;

import com.pio.raonback.repository.ChatRepository;
import com.pio.raonback.repository.RefreshTokenRepository;
import com.pio.raonback.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RaonScheduler {

  private final RefreshTokenRepository refreshTokenRepository;
  private final ChatRepository chatRepository;
  private final TradeRepository tradeRepository;

  @Scheduled(cron = "0 0 * * * *")
  public void cleanUpData() {
    deleteUselessChats();
    deleteUselessTrades();
    deleteExpiredRefreshTokens();
  }

  private void deleteUselessChats() {
    chatRepository.deleteAllByBuyerIsDeletedTrueAndSellerIsDeletedTrue();
    chatRepository.deleteAllByCreatedAtBeforeAndLastMessageAtNull(LocalDateTime.now().minusMinutes(30));
  }

  private void deleteUselessTrades() {
    tradeRepository.deleteAllByBuyerIsDeletedTrueAndSellerIsDeletedTrue();
  }

  private void deleteExpiredRefreshTokens() {
    refreshTokenRepository.deleteAllByExpiresAtBefore(LocalDateTime.now());
  }

}
