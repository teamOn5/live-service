package com.teamon5.livepick.livecoupon.domain.model;

import com.msa.common.core.error.BusinessException;
import com.msa.common.core.error.CommonErrorCode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LiveCoupon {

  private Long id;
  private final UUID liveUuid;
  private final UUID couponId;
  private final int unlockViewerCount;
  private LocalDateTime unlockedAt;
  private final int displayOrder;
  private final LocalDateTime createdAt;

  public LiveCoupon(UUID liveUuid, UUID couponId, int unlockViewerCount, int displayOrder,
      LocalDateTime createdAt) {
    if (unlockViewerCount < 0 || displayOrder < 0) {
      throw new BusinessException(CommonErrorCode.INVALID_INPUT);
    }
    this.liveUuid = Objects.requireNonNull(liveUuid);
    this.couponId = Objects.requireNonNull(couponId);
    this.unlockViewerCount = unlockViewerCount;
    this.displayOrder = displayOrder;
    this.createdAt = Objects.requireNonNull(createdAt);
  }
}
