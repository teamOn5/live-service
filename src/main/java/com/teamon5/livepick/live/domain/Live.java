package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.BusinessException;
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

  private static final int MAX_PRODUCTS = 5;
  private static final int MAX_COUPONS = 5;
  private static final int MAX_HASHTAGS = 5;
  private static final int MAX_HOSTS = 5;

  private Long id;
  private final UUID liveUuid;
  private final UUID shopId;
  private final UUID createdBy;
  @Getter(AccessLevel.NONE)
  private final LiveTitle title;
  @Getter(AccessLevel.NONE)
  private final LiveDescription description;
  private Long thumbnailMediaId;
  private Long categoryId;
  @Getter(AccessLevel.NONE)
  private final LiveSchedule schedule;
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
  private final List<LiveProduct> products = new ArrayList<>();
  private final List<LiveCoupon> coupons = new ArrayList<>();
  private final List<LiveHashtag> hashtags = new ArrayList<>();
  private final List<LiveHost> hosts = new ArrayList<>();
  private final List<LiveNotice> notices = new ArrayList<>();

  private Live(UUID liveUuid, UUID shopId, UUID createdBy, Long categoryId, Long thumbnailMediaId,
      LiveTitle title, LiveDescription description, LiveSchedule schedule, LocalDateTime now) {
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
        new LiveTitle(title), description == null ? null : new LiveDescription(description),
        new LiveSchedule(scheduledStartAt, scheduledEndAt), now);
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

  public void addProduct(UUID productId, UUID productOptionId, String optionName,
      int displayOrder, LocalDateTime now) {
    if (products.size() >= MAX_PRODUCTS) {
      throw new BusinessException(LiveErrorCode.ITEM_LIMIT_EXCEEDED);
    }
    if (products.stream().anyMatch(p -> p.getProductOptionId().equals(productOptionId))) {
      throw new BusinessException(LiveErrorCode.DUPLICATE_LIVE_ITEM);
    }
    products.add(new LiveProduct(productId, productOptionId, new OptionName(optionName),
        displayOrder, now));
  }

  public void addCoupon(UUID couponId, int unlockViewerCount, int displayOrder,
      LocalDateTime now) {
    if (coupons.size() >= MAX_COUPONS) {
      throw new BusinessException(LiveErrorCode.ITEM_LIMIT_EXCEEDED);
    }
    if (coupons.stream().anyMatch(c -> c.getCouponId().equals(couponId))) {
      throw new BusinessException(LiveErrorCode.DUPLICATE_LIVE_ITEM);
    }
    coupons.add(new LiveCoupon(couponId, unlockViewerCount, displayOrder, now));
  }

  public void addHashtag(String tag) {
    Hashtag hashtag = new Hashtag(tag);
    if (hashtags.size() >= MAX_HASHTAGS) {
      throw new BusinessException(LiveErrorCode.ITEM_LIMIT_EXCEEDED);
    }
    if (hashtags.stream().anyMatch(h -> h.getTag().equals(hashtag.value()))) {
      throw new BusinessException(LiveErrorCode.DUPLICATE_LIVE_ITEM);
    }
    hashtags.add(new LiveHashtag(hashtag));
  }

  public void addHost(String name, Long imageMediaId, int displayOrder) {
    HostName hostName = new HostName(name);
    if (hosts.size() >= MAX_HOSTS) {
      throw new BusinessException(LiveErrorCode.ITEM_LIMIT_EXCEEDED);
    }
    hosts.add(new LiveHost(hostName, imageMediaId, displayOrder));
  }

  public void addNotice(String content, UUID author, LocalDateTime now) {
    notices.add(new LiveNotice(new NoticeContent(content), author, now));
  }

  public List<LiveProduct> getProducts() {
    return Collections.unmodifiableList(products);
  }

  public List<LiveCoupon> getCoupons() {
    return Collections.unmodifiableList(coupons);
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
