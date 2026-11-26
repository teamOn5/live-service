package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.BusinessException;

record NoticeContent(String value) {

  private static final int MAX_LENGTH = 50;

  NoticeContent {
    if (value == null || value.isBlank() || value.length() > MAX_LENGTH) {
      throw new BusinessException(LiveErrorCode.INVALID_NOTICE_CONTENT);
    }
  }
}
