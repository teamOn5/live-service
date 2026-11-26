package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.BusinessException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class Live {

  private Long id;
  private final UUID liveUuid;
  private final UUID shopId;
  private final UUID createdBy;
  @Getter(AccessLevel.NONE)
  private final Title title;
  @Getter(AccessLevel.NONE)
  private final Description description;
  private Long thumbnailMediaId;
  private Long categoryId;
  @Getter(AccessLevel.NONE)
  private final Schedule schedule;
  private LiveStatus status;
  private LiveEndReason endReason;
  private LiveCancelReason cancelReason;
  private boolean chatEnabled;
  private String streamKey;
  private String ingestUrl;
  private String playbackUrl;
  private String chatChannel;
  private LocalDateTime startedAt;
  private LocalDateTime endedAt;
  private LocalDateTime canceledAt;
  private UUID endedBy;
  private UUID canceledBy;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;

  private Live(UUID liveUuid, UUID shopId, UUID createdBy, Long categoryId, Long thumbnailMediaId,
      Title title, Description description, Schedule schedule, LocalDateTime now) {
    this.liveUuid = liveUuid;
    this.shopId = Objects.requireNonNull(shopId);
    this.createdBy = Objects.requireNonNull(createdBy);
    this.categoryId = Objects.requireNonNull(categoryId);
    this.thumbnailMediaId = thumbnailMediaId;
    this.title = title;
    this.description = description;
    this.schedule = schedule;
    this.status = LiveStatus.CREATED;
    this.chatEnabled = true;
    this.createdAt = Objects.requireNonNull(now);
    this.updatedAt = now;
  }

  public static Live create(UUID shopId, UUID createdBy, Long categoryId, Long thumbnailMediaId,
      String title, String description, LocalDateTime scheduledStartAt,
      LocalDateTime scheduledEndAt, LocalDateTime now) {
    return new Live(UUID.randomUUID(), shopId, createdBy, categoryId, thumbnailMediaId,
        new Title(title), description == null ? null : new Description(description),
        new Schedule(scheduledStartAt, scheduledEndAt), now);
  }

  public String getTitle() {
    return title.value();
  }

  public String getDescription() {
    return description == null ? null : description.value();
  }

  public LocalDateTime getScheduledStartAt() {
    return schedule.scheduledStartAt();
  }

  public LocalDateTime getScheduledEndAt() {
    return schedule.scheduledEndAt();
  }

  public void start(LocalDateTime now) {
    changeStatus(LiveStatus.CREATED, LiveStatus.RUNNING, now);
    this.startedAt = now;
  }

  public void pause(LocalDateTime now) {
    changeStatus(LiveStatus.RUNNING, LiveStatus.PAUSED, now);
  }

  public void resume(LocalDateTime now) {
    changeStatus(LiveStatus.PAUSED, LiveStatus.RUNNING, now);
  }

  public void end(LocalDateTime now, LiveEndReason reason, UUID endedBy) {
    changeStatus(LiveStatus.RUNNING, LiveStatus.TERMINATED, now);
    this.endedAt = now;
    this.endReason = reason;
    this.endedBy = endedBy;
  }

  public void cancel(LocalDateTime now, LiveCancelReason reason, UUID canceledBy) {
    changeStatus(LiveStatus.CREATED, LiveStatus.CANCELED, now);
    this.canceledAt = now;
    this.cancelReason = reason;
    this.canceledBy = canceledBy;
  }

  private void changeStatus(LiveStatus from, LiveStatus to, LocalDateTime now) {
    if (this.status != from) {
      throw new BusinessException(LiveErrorCode.INVALID_LIVE_STATUS_TRANSITION);
    }
    this.status = to;
    this.updatedAt = now;
  }

  private record Title(String value) {

    private static final int MAX_LENGTH = 20;

    Title {
      if (value == null || value.isBlank() || value.length() > MAX_LENGTH) {
        throw new BusinessException(LiveErrorCode.INVALID_LIVE_TITLE);
      }
    }
  }

  private record Description(String value) {

    private static final int MAX_LENGTH = 100;

    Description {
      if (value == null || value.length() > MAX_LENGTH) {
        throw new BusinessException(LiveErrorCode.INVALID_LIVE_DESCRIPTION);
      }
    }
  }

  private record Schedule(LocalDateTime scheduledStartAt, LocalDateTime scheduledEndAt) {

    private static final int MIN_DURATION_MINUTES = 10;

    Schedule {
      if (scheduledStartAt == null || scheduledEndAt == null
          || scheduledEndAt.isBefore(scheduledStartAt.plusMinutes(MIN_DURATION_MINUTES))) {
        throw new BusinessException(LiveErrorCode.INVALID_LIVE_SCHEDULE);
      }
    }
  }
}
