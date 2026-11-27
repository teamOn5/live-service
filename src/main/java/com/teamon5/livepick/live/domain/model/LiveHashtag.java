package com.teamon5.livepick.live.domain.model;

import com.msa.common.core.error.BusinessException;
import com.teamon5.livepick.live.domain.exception.LiveErrorCode;
import lombok.Getter;

@Getter
public class LiveHashtag {

  private static final int MAX_LENGTH = 10;

  private Long id;
  private final String tag;

  LiveHashtag(String tag) {
    if (tag == null || tag.isBlank() || tag.length() > MAX_LENGTH) {
      throw new BusinessException(LiveErrorCode.INVALID_HASHTAG);
    }
    this.tag = tag;
  }
}
