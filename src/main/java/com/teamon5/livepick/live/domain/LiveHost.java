package com.teamon5.livepick.live.domain;

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
    this.name = name;
    this.imageMediaId = imageMediaId;
    this.displayOrder = displayOrder;
  }

  public String getName() {
    return name.value();
  }
}
