package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.BusinessException;

public record LiveDescription(String value) {

  private static final int MAX_LENGTH = 100;

  public LiveDescription {
    if (value == null || value.length() > MAX_LENGTH) {
      throw new BusinessException(LiveErrorCode.INVALID_LIVE_DESCRIPTION);
    }
  }
}
