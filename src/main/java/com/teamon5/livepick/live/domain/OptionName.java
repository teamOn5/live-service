package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.BusinessException;

record OptionName(String value) {

  private static final int MAX_LENGTH = 255;

  OptionName {
    if (value == null || value.isBlank() || value.length() > MAX_LENGTH) {
      throw new BusinessException(LiveErrorCode.INVALID_OPTION_NAME);
    }
  }
}
