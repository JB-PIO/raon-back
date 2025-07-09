package com.pio.raonback.scheduler;

import com.pio.raonback.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class RaonScheduler {

  private final RefreshTokenRepository refreshTokenRepository;

  @Scheduled(cron = "* * * * * *")
  public void cleanUpExpiredRefreshTokens() {
    Date now = Date.from(Instant.now());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    refreshTokenRepository.deleteAllByExpiresAtBefore(simpleDateFormat.format(now));
  }

}
