package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.BusinessException;
import com.msa.common.core.error.CommonErrorCode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LiveProduct {

  private static final int MAX_OPTION_NAME_LENGTH = 255;

  private Long id;
  private final UUID productId;
  private final UUID productOptionId;
  private final String optionName;
  private final int displayOrder;
  private final LocalDateTime createdAt;

  LiveProduct(UUID productId, UUID productOptionId, String optionName, int displayOrder,
      LocalDateTime createdAt) {
    if (optionName == null || optionName.isBlank()
        || optionName.length() > MAX_OPTION_NAME_LENGTH) {
      throw new BusinessException(LiveErrorCode.INVALID_OPTION_NAME);
    }
    if (displayOrder < 0) {
      throw new BusinessException(CommonErrorCode.INVALID_INPUT);
    }
    this.productId = Objects.requireNonNull(productId);
    this.productOptionId = Objects.requireNonNull(productOptionId);
    this.optionName = optionName;
    this.displayOrder = displayOrder;
    this.createdAt = Objects.requireNonNull(createdAt);
  }
}
