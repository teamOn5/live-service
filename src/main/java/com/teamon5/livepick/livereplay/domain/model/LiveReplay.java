package com.teamon5.livepick.livereplay.domain.model;

import com.teamon5.livepick.livereplay.domain.enums.ReplayStatus;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LiveReplay {

  private Long id;
  private final UUID liveUuid;
  private Long videoMediaId;
  private ReplayStatus status;
  private int retryCount;
  private Integer durationSec;
  private final LocalDateTime createdAt;
  private LocalDateTime readyAt;

  private LiveReplay(UUID liveUuid, LocalDateTime now) {
    this.liveUuid = Objects.requireNonNull(liveUuid);
    this.status = ReplayStatus.PROCESSING;
    this.createdAt = Objects.requireNonNull(now);
  }

  public static LiveReplay create(UUID liveUuid, LocalDateTime now) {
    return new LiveReplay(liveUuid, now);
  }
}
