package com.teamon5.livepick.live.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class LiveProduct {

  private Long id;
  private final UUID productId;
  private final UUID productOptionId;
  @Getter(AccessLevel.NONE)
  private final OptionName optionName;
  private final int displayOrder;
  private final LocalDateTime createdAt;

  LiveProduct(UUID productId, UUID productOptionId, OptionName optionName, int displayOrder,
      LocalDateTime createdAt) {
    this.productId = Objects.requireNonNull(productId);
    this.productOptionId = Objects.requireNonNull(productOptionId);
    this.optionName = optionName;
    this.displayOrder = displayOrder;
    this.createdAt = Objects.requireNonNull(createdAt);
  }

  public String getOptionName() {
    return optionName.value();
  }
}
