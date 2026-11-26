package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.BusinessException;
import com.msa.common.core.error.CommonErrorCode;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class LiveHost {

  private Long id;
  @Getter(AccessLevel.NONE)
  private final HostName name;
  private final Long imageMediaId;
  private final int displayOrder;

  LiveHost(HostName name, Long imageMediaId, int displayOrder) {
    if (displayOrder < 0) {
      throw new BusinessException(CommonErrorCode.INVALID_INPUT);
    }
    this.name = name;
    this.imageMediaId = imageMediaId;
    this.displayOrder = displayOrder;
  }

  public String getName() {
    return name.value();
  }
}
