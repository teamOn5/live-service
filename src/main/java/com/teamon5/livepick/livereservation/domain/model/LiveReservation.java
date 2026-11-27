package com.teamon5.livepick.livereservation.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LiveReservation {

  private Long id;
  private final UUID liveUuid;
  private final UUID userId;
  private final LocalDateTime createdAt;

  private LiveReservation(UUID liveUuid, UUID userId, LocalDateTime createdAt) {
    this.liveUuid = Objects.requireNonNull(liveUuid);
    this.userId = Objects.requireNonNull(userId);
    this.createdAt = Objects.requireNonNull(createdAt);
  }

  public static LiveReservation create(UUID liveUuid, UUID userId, LocalDateTime now) {
    return new LiveReservation(liveUuid, userId, now);
  }
}
