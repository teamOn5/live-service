package com.teamon5.livepick.liveproduct.domain.model;

import com.msa.common.core.error.BusinessException;
import com.msa.common.core.error.CommonErrorCode;
import com.teamon5.livepick.liveproduct.domain.exception.LiveProductErrorCode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LiveProduct {

  private static final int MAX_OPTION_NAME_LENGTH = 255;

  private Long id;
  private final UUID liveUuid;
  private final UUID productId;
  private final UUID productOptionId;
  private final String optionName;
  private final int displayOrder;
  private final LocalDateTime createdAt;

  public LiveProduct(UUID liveUuid, UUID productId, UUID productOptionId, String optionName,
      int displayOrder, LocalDateTime createdAt) {
    if (optionName == null || optionName.isBlank()
        || optionName.length() > MAX_OPTION_NAME_LENGTH) {
      throw new BusinessException(LiveProductErrorCode.INVALID_OPTION_NAME);
    }
    if (displayOrder < 0) {
      throw new BusinessException(CommonErrorCode.INVALID_INPUT);
    }
    this.liveUuid = Objects.requireNonNull(liveUuid);
    this.productId = Objects.requireNonNull(productId);
    this.productOptionId = Objects.requireNonNull(productOptionId);
    this.optionName = optionName;
    this.displayOrder = displayOrder;
    this.createdAt = Objects.requireNonNull(createdAt);
  }
}
