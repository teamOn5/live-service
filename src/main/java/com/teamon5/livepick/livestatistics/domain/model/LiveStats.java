package com.teamon5.livepick.livestatistics.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class LiveStats {

  private final UUID liveUuid;
  @Getter(AccessLevel.NONE)
  private final LiveStatistics statistics;
  private final LocalDateTime finalizedAt;

  private LiveStats(UUID liveUuid, LiveStatistics statistics, LocalDateTime finalizedAt) {
    this.liveUuid = Objects.requireNonNull(liveUuid);
    this.statistics = statistics;
    this.finalizedAt = Objects.requireNonNull(finalizedAt);
  }

  public static LiveStats create(UUID liveUuid, int peakViewerCount, int uniqueViewerCount,
      int chatCount, LocalDateTime finalizedAt) {
    return new LiveStats(liveUuid,
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
