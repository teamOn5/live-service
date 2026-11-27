package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.BusinessException;

record Hashtag(String value) {

  private static final int MAX_LENGTH = 10;

  Hashtag {
    if (value == null || value.isBlank() || value.length() > MAX_LENGTH) {
      throw new BusinessException(LiveErrorCode.INVALID_HASHTAG);
    }
  }
}
