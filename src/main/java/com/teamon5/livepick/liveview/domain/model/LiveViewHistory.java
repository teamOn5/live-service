package com.teamon5.livepick.liveview.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LiveViewHistory {

  private Long id;
  private final UUID userId;
  private final UUID liveUuid;
  private final UUID shopId;
  private final LocalDateTime firstViewedAt;
  private LocalDateTime lastViewedAt;

  private LiveViewHistory(UUID userId, UUID liveUuid, UUID shopId, LocalDateTime now) {
    this.userId = Objects.requireNonNull(userId);
    this.liveUuid = Objects.requireNonNull(liveUuid);
    this.shopId = Objects.requireNonNull(shopId);
    this.firstViewedAt = Objects.requireNonNull(now);
    this.lastViewedAt = now;
  }

  public static LiveViewHistory create(UUID userId, UUID liveUuid, UUID shopId,
      LocalDateTime now) {
    return new LiveViewHistory(userId, liveUuid, shopId, now);
  }

  public void visit(LocalDateTime now) {
    Objects.requireNonNull(now);
    if (now.isAfter(this.lastViewedAt)) {
      this.lastViewedAt = now;
    }
  }
}
