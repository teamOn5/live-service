package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.BusinessException;
import com.msa.common.core.error.CommonErrorCode;
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

  private LiveStats(Long liveId, int peakViewerCount, int uniqueViewerCount, int chatCount,
      LocalDateTime finalizedAt) {
    this.liveId = Objects.requireNonNull(liveId);
    this.peakViewerCount = peakViewerCount;
    this.uniqueViewerCount = uniqueViewerCount;
    this.chatCount = chatCount;
    this.finalizedAt = Objects.requireNonNull(finalizedAt);
  }

  public static LiveStats create(Long liveId, int peakViewerCount, int uniqueViewerCount,
      int chatCount, LocalDateTime finalizedAt) {
    if (peakViewerCount < 0 || uniqueViewerCount < 0 || chatCount < 0) {
      throw new BusinessException(CommonErrorCode.INVALID_INPUT);
    }
    return new LiveStats(liveId, peakViewerCount, uniqueViewerCount, chatCount, finalizedAt);
  }
}
