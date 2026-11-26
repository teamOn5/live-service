package com.teamon5.livepick.live.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.msa.common.core.error.BusinessException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class LiveStatsTest {

  private static final LocalDateTime NOW = LocalDateTime.of(2026, 12, 1, 12, 0);

  @Test
  void 집계_값을_그대로_보관한다() {
    LiveStats stats = LiveStats.create(1L, 120, 300, 45, NOW);

    assertThat(stats.getPeakViewerCount()).isEqualTo(120);
    assertThat(stats.getUniqueViewerCount()).isEqualTo(300);
    assertThat(stats.getChatCount()).isEqualTo(45);
    assertThat(stats.getFinalizedAt()).isEqualTo(NOW);
  }

  @Test
  void 집계_값이_음수이면_예외가_발생한다() {
    assertThatThrownBy(() -> LiveStats.create(1L, -1, 0, 0, NOW))
        .isInstanceOf(BusinessException.class);
  }
}
