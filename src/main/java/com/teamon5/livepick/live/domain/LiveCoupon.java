package com.teamon5.livepick.live.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LiveCoupon {

  private Long id;
  private final UUID couponId;
  private final int unlockViewerCount;
  private LocalDateTime unlockedAt;
  private final int displayOrder;
  private final LocalDateTime createdAt;

  LiveCoupon(UUID couponId, int unlockViewerCount, int displayOrder, LocalDateTime createdAt) {
    this.couponId = Objects.requireNonNull(couponId);
    this.unlockViewerCount = unlockViewerCount;
    this.displayOrder = displayOrder;
    this.createdAt = Objects.requireNonNull(createdAt);
  }
}
