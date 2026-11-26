package com.teamon5.livepick.live.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class LiveStats {

  private final Long liveId;
  @Getter(AccessLevel.NONE)
  private final LiveStatistics statistics;
  private final LocalDateTime finalizedAt;

  private LiveStats(Long liveId, LiveStatistics statistics, LocalDateTime finalizedAt) {
    this.liveId = Objects.requireNonNull(liveId);
    this.statistics = statistics;
    this.finalizedAt = Objects.requireNonNull(finalizedAt);
  }

  public static LiveStats create(Long liveId, int peakViewerCount, int uniqueViewerCount,
      int chatCount, LocalDateTime finalizedAt) {
    return new LiveStats(liveId,
        new LiveStatistics(peakViewerCount, uniqueViewerCount, chatCount), finalizedAt);
  }

  public int getPeakViewerCount() {
    return statistics.peakViewerCount();
  }

  public int getUniqueViewerCount() {
    return statistics.uniqueViewerCount();
  }

  public int getChatCount() {
    return statistics.chatCount();
  }
}
