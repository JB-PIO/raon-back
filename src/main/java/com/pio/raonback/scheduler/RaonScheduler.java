package com.pio.raonback.scheduler;

import com.pio.raonback.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RaonScheduler {

  private final RefreshTokenRepository refreshTokenRepository;

  @Scheduled(cron = "0 0 * * * *")
  public void cleanUpExpiredRefreshTokens() {
    refreshTokenRepository.deleteAllByExpiresAtBefore(LocalDateTime.now());
  }

}
