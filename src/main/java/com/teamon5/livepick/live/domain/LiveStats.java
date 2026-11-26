package com.teamon5.livepick.live.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class LiveStats {

  private final Long liveId;
  private final int peakViewerCount;
  private final int uniqueViewerCount;
  private final int chatCount;
  private final LocalDateTime finalizedAt;

  public LiveStats(Long liveId, int peakViewerCount, int uniqueViewerCount, int chatCount,
      LocalDateTime finalizedAt) {
    this.liveId = Objects.requireNonNull(liveId);
    this.peakViewerCount = nonNegative(peakViewerCount);
    this.uniqueViewerCount = nonNegative(uniqueViewerCount);
    this.chatCount = nonNegative(chatCount);
    this.finalizedAt = Objects.requireNonNull(finalizedAt);
  }

  private static int nonNegative(int count) {
    if (count < 0) {
      throw new IllegalArgumentException("집계 값은 0 이상이어야 합니다.");
    }
    return count;
  }
}
