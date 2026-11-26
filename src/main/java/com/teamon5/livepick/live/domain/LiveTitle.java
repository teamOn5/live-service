package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.BusinessException;

record LiveTitle(String value) {

  private static final int MAX_LENGTH = 20;

  LiveTitle {
    if (value == null || value.isBlank() || value.length() > MAX_LENGTH) {
      throw new BusinessException(LiveErrorCode.INVALID_LIVE_TITLE);
    }
  }
}
