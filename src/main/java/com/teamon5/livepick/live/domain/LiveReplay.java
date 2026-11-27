package com.teamon5.livepick.live.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class LiveReplay {

  private Long id;
  private final Long liveId;
  private Long videoMediaId;
  private ReplayStatus status;
  private int retryCount;
  private Integer durationSec;
  private final LocalDateTime createdAt;
  private LocalDateTime readyAt;

  private LiveReplay(Long liveId, LocalDateTime now) {
    this.liveId = Objects.requireNonNull(liveId);
    this.status = ReplayStatus.PROCESSING;
    this.createdAt = Objects.requireNonNull(now);
  }

  public static LiveReplay create(Long liveId, LocalDateTime now) {
    return new LiveReplay(liveId, now);
  }
}
