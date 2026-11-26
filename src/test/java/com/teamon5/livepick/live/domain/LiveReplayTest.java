package com.teamon5.livepick.live.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class LiveReplayTest {

  private static final LocalDateTime NOW = LocalDateTime.of(2026, 12, 1, 9, 0);

  @Test
  void 생성하면_변환중_상태이고_재시도_횟수는_0이다() {
    LiveReplay replay = LiveReplay.create(1L, NOW);

    assertThat(replay.getStatus()).isEqualTo(ReplayStatus.PROCESSING);
    assertThat(replay.getRetryCount()).isZero();
    assertThat(replay.getLiveId()).isEqualTo(1L);
    assertThat(replay.getReadyAt()).isNull();
  }
}
