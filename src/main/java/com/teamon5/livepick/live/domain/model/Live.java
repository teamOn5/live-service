package com.teamon5.livepick.live.domain.model;

import com.msa.common.core.error.BusinessException;
import com.teamon5.livepick.live.domain.enums.LiveCancelReason;
import com.teamon5.livepick.live.domain.enums.LiveEndReason;
import com.teamon5.livepick.live.domain.enums.LiveStatus;
import com.teamon5.livepick.live.domain.exception.LiveErrorCode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class Live {

  private static final int MAX_TITLE_LENGTH = 20;
  private static final int MAX_DESCRIPTION_LENGTH = 100;
  private static final int MAX_HASHTAGS = 5;
  private static final int MAX_HOSTS = 5;

  private Long id;
  private final UUID liveUuid;
  private final UUID shopId;
  private final UUID createdBy;
  private final String title;
  private final String description;
  private Long thumbnailMediaId;
  private Long categoryId;

  @Getter(AccessLevel.NONE)
  private final LiveSchedule schedule;
  private LiveStatus status;
  private LiveEndReason endReason;
  private LiveCancelReason cancelReason;
  private boolean chatEnabled;
  private LocalDateTime startedAt;
  private LocalDateTime endedAt;
  private LocalDateTime canceledAt;
  private UUID endedBy;
  private UUID canceledBy;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;
  private final List<LiveHashtag> hashtags = new ArrayList<>();
  private final List<LiveHost> hosts = new ArrayList<>();
  private final List<LiveNotice> notices = new ArrayList<>();

  private Live(UUID liveUuid, UUID shopId, UUID createdBy, Long categoryId, Long thumbnailMediaId,
      String title, String description, LiveSchedule schedule, LocalDateTime now) {
    if (title == null || title.isBlank() || title.length() > MAX_TITLE_LENGTH) {
      throw new BusinessException(LiveErrorCode.INVALID_LIVE_TITLE);
    }
    if (description != null && description.length() > MAX_DESCRIPTION_LENGTH) {
      throw new BusinessException(LiveErrorCode.INVALID_LIVE_DESCRIPTION);
    }
    this.liveUuid = liveUuid;
    this.shopId = Objects.requireNonNull(shopId);
    this.createdBy = Objects.requireNonNull(createdBy);
    this.categoryId = Objects.requireNonNull(categoryId);
    this.thumbnailMediaId = thumbnailMediaId;
    this.title = title;
    this.description = description;
    this.schedule = Objects.requireNonNull(schedule);
    this.status = LiveStatus.CREATED;
    this.chatEnabled = true;
    this.createdAt = Objects.requireNonNull(now);
    this.updatedAt = now;
  }

  public static Live create(UUID shopId, UUID createdBy, Long categoryId, Long thumbnailMediaId,
      String title, String description, LocalDateTime scheduledStartAt,
      LocalDateTime scheduledEndAt, LocalDateTime now) {
    return new Live(UUID.randomUUID(), shopId, createdBy, categoryId, thumbnailMediaId,
        title, description, new LiveSchedule(scheduledStartAt, scheduledEndAt), now);
  }

  public LocalDateTime getScheduledStartAt() {
    return schedule.scheduledStartAt();
  }

  public LocalDateTime getScheduledEndAt() {
    return schedule.scheduledEndAt();
  }

  public void start(LocalDateTime now) {
    changeStatus(LiveStatus.RUNNING, now, LiveStatus.CREATED);
    this.startedAt = now;
  }

  public void pause(LocalDateTime now) {
    changeStatus(LiveStatus.PAUSED, now, LiveStatus.RUNNING);
  }

  public void resume(LocalDateTime now) {
    changeStatus(LiveStatus.RUNNING, now, LiveStatus.PAUSED);
  }

  public void end(LocalDateTime now, LiveEndReason reason, UUID endedBy) {
    changeStatus(LiveStatus.TERMINATED, now, LiveStatus.RUNNING, LiveStatus.PAUSED);
    this.endedAt = now;
    this.endReason = reason;
    this.endedBy = endedBy;
  }

  public void cancel(LocalDateTime now, LiveCancelReason reason, UUID canceledBy) {
    changeStatus(LiveStatus.CANCELED, now, LiveStatus.CREATED);
    this.canceledAt = now;
    this.cancelReason = reason;
    this.canceledBy = canceledBy;
  }

  public void addHashtag(String tag) {
    LiveHashtag hashtag = new LiveHashtag(tag);
    if (hashtags.size() >= MAX_HASHTAGS) {
      throw new BusinessException(LiveErrorCode.ITEM_LIMIT_EXCEEDED);
    }
    if (hashtags.stream().anyMatch(h -> h.getTag().equals(tag))) {
      throw new BusinessException(LiveErrorCode.DUPLICATE_LIVE_ITEM);
    }
    hashtags.add(hashtag);
  }

  public void addHost(String name, Long imageMediaId, int displayOrder) {
    if (hosts.size() >= MAX_HOSTS) {
      throw new BusinessException(LiveErrorCode.ITEM_LIMIT_EXCEEDED);
    }
    hosts.add(new LiveHost(name, imageMediaId, displayOrder));
  }

  public void addNotice(String content, UUID author, LocalDateTime now) {
    notices.add(new LiveNotice(content, author, now));
  }

  public List<LiveHashtag> getHashtags() {
    return Collections.unmodifiableList(hashtags);
  }

  public List<LiveHost> getHosts() {
    return Collections.unmodifiableList(hosts);
  }

  public List<LiveNotice> getNotices() {
    return Collections.unmodifiableList(notices);
  }

  private void changeStatus(LiveStatus to, LocalDateTime now, LiveStatus... allowedFrom) {
    Objects.requireNonNull(now);
    if (!Arrays.asList(allowedFrom).contains(this.status)) {
      throw new BusinessException(LiveErrorCode.INVALID_LIVE_STATUS_TRANSITION);
    }
    this.status = to;
    this.updatedAt = now;
  }
}
