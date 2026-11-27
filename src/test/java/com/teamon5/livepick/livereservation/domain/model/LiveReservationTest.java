package com.teamon5.livepick.livereservation.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class LiveReservationTest {

  private static final LocalDateTime NOW = LocalDateTime.of(2026, 12, 1, 9, 0);
  private static final UUID LIVE_UUID = UUID.randomUUID();

  @Test
  void 생성하면_방송과_회원과_예약_시각이_기록된다() {
    UUID user = UUID.randomUUID();

    LiveReservation reservation = LiveReservation.create(LIVE_UUID, user, NOW);

    assertThat(reservation.getLiveUuid()).isEqualTo(LIVE_UUID);
    assertThat(reservation.getUserId()).isEqualTo(user);
    assertThat(reservation.getCreatedAt()).isEqualTo(NOW);
    assertThat(reservation.getId()).isNull();
  }

  @Test
  void 방송_아이디나_회원_아이디가_null이면_생성할_수_없다() {
    assertThatThrownBy(() -> LiveReservation.create(null, UUID.randomUUID(), NOW))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> LiveReservation.create(LIVE_UUID, null, NOW))
        .isInstanceOf(NullPointerException.class);
  }
}
