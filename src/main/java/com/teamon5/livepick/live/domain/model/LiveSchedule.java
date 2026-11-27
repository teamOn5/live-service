package com.teamon5.livepick.live.domain.model;

import com.msa.common.core.error.BusinessException;
import com.teamon5.livepick.live.domain.exception.LiveErrorCode;
import java.time.LocalDateTime;

record LiveSchedule(LocalDateTime scheduledStartAt, LocalDateTime scheduledEndAt) {

  private static final int MIN_DURATION_MINUTES = 10;

  LiveSchedule {
    if (scheduledStartAt == null || scheduledEndAt == null
        || scheduledEndAt.isBefore(scheduledStartAt.plusMinutes(MIN_DURATION_MINUTES))) {
      throw new BusinessException(LiveErrorCode.INVALID_LIVE_SCHEDULE);
    }
  }
}
