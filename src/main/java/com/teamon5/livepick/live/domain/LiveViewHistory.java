package com.teamon5.livepick.live.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LiveViewHistory {

  private Long id;
  private final UUID userId;
  private final Long liveId;
  private final UUID shopId;
  private final LocalDateTime firstViewedAt;
  private LocalDateTime lastViewedAt;

  private LiveViewHistory(UUID userId, Long liveId, UUID shopId, LocalDateTime now) {
    this.userId = Objects.requireNonNull(userId);
    this.liveId = Objects.requireNonNull(liveId);
    this.shopId = Objects.requireNonNull(shopId);
    this.firstViewedAt = Objects.requireNonNull(now);
    this.lastViewedAt = now;
  }

  public static LiveViewHistory create(UUID userId, Long liveId, UUID shopId, LocalDateTime now) {
    return new LiveViewHistory(userId, liveId, shopId, now);
  }

  public void visit(LocalDateTime now) {
    this.lastViewedAt = Objects.requireNonNull(now);
  }
}
