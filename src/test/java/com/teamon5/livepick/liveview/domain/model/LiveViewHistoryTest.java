package com.teamon5.livepick.liveview.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class LiveViewHistoryTest {

  private static final LocalDateTime NOW = LocalDateTime.of(2026, 12, 1, 9, 0);
  private static final UUID LIVE_UUID = UUID.randomUUID();

  @Test
  void 생성하면_처음_입장과_마지막_입장_시각이_같다() {
    LiveViewHistory history =
        LiveViewHistory.create(UUID.randomUUID(), LIVE_UUID, UUID.randomUUID(), NOW);

    assertThat(history.getFirstViewedAt()).isEqualTo(NOW);
    assertThat(history.getLastViewedAt()).isEqualTo(NOW);
  }

  @Test
  void 다시_입장하면_마지막_입장_시각만_갱신된다() {
    LiveViewHistory history =
        LiveViewHistory.create(UUID.randomUUID(), LIVE_UUID, UUID.randomUUID(), NOW);

    history.visit(NOW.plusMinutes(30));

    assertThat(history.getFirstViewedAt()).isEqualTo(NOW);
    assertThat(history.getLastViewedAt()).isEqualTo(NOW.plusMinutes(30));
  }

  @Test
  void 과거_시각으로_방문하면_마지막_입장_시각은_바뀌지_않는다() {
    LiveViewHistory history =
        LiveViewHistory.create(UUID.randomUUID(), LIVE_UUID, UUID.randomUUID(), NOW);
    history.visit(NOW.plusMinutes(30));

    history.visit(NOW.plusMinutes(10));

    assertThat(history.getLastViewedAt()).isEqualTo(NOW.plusMinutes(30));
  }

  @Test
  void 회원_방송_쇼핑몰_중_null이_있으면_생성할_수_없다() {
    assertThatThrownBy(() -> LiveViewHistory.create(null, LIVE_UUID, UUID.randomUUID(), NOW))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() ->
        LiveViewHistory.create(UUID.randomUUID(), null, UUID.randomUUID(), NOW))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> LiveViewHistory.create(UUID.randomUUID(), LIVE_UUID, null, NOW))
        .isInstanceOf(NullPointerException.class);
  }
}
