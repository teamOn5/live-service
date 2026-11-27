package com.teamon5.livepick.livereplay.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.teamon5.livepick.livereplay.domain.enums.ReplayStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class LiveReplayTest {

  private static final LocalDateTime NOW = LocalDateTime.of(2026, 12, 1, 9, 0);
  private static final UUID LIVE_UUID = UUID.randomUUID();

  @Test
  void 생성하면_변환중_상태이고_재시도_횟수는_0이다() {
    LiveReplay replay = LiveReplay.create(LIVE_UUID, NOW);

    assertThat(replay.getStatus()).isEqualTo(ReplayStatus.PROCESSING);
    assertThat(replay.getRetryCount()).isZero();
    assertThat(replay.getLiveUuid()).isEqualTo(LIVE_UUID);
    assertThat(replay.getReadyAt()).isNull();
  }
}
