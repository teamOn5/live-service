package com.teamon5.livepick.live.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LiveReservation {

  private Long id;
  private final Long liveId;
  private final UUID userId;
  private final LocalDateTime createdAt;

  private LiveReservation(Long liveId, UUID userId, LocalDateTime createdAt) {
    this.liveId = Objects.requireNonNull(liveId);
    this.userId = Objects.requireNonNull(userId);
    this.createdAt = Objects.requireNonNull(createdAt);
  }

  public static LiveReservation create(Long liveId, UUID userId, LocalDateTime now) {
    return new LiveReservation(liveId, userId, now);
  }
}
