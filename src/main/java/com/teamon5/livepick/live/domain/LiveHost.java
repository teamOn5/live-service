package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.BusinessException;
import com.msa.common.core.error.CommonErrorCode;
import lombok.Getter;

@Getter
public class LiveHost {

  private static final int MAX_NAME_LENGTH = 10;

  private Long id;
  private final String name;
  private final Long imageMediaId;
  private final int displayOrder;

  LiveHost(String name, Long imageMediaId, int displayOrder) {
    if (name == null || name.isBlank() || name.length() > MAX_NAME_LENGTH) {
      throw new BusinessException(LiveErrorCode.INVALID_HOST_NAME);
    }
    if (displayOrder < 0) {
      throw new BusinessException(CommonErrorCode.INVALID_INPUT);
    }
    this.name = name;
    this.imageMediaId = imageMediaId;
    this.displayOrder = displayOrder;
  }
}
