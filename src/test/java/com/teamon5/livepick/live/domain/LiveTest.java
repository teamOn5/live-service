package com.teamon5.livepick.live.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.msa.common.core.error.BusinessException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class LiveTest {

  private static final LocalDateTime NOW = LocalDateTime.of(2026, 12, 1, 9, 0);
  private static final UUID SELLER = UUID.randomUUID();

  @Test
  void 생성하면_예정_상태이고_채팅이_활성화되며_uuid가_발급된다() {
    Live live = createLive();

    assertThat(live.getStatus()).isEqualTo(LiveStatus.CREATED);
    assertThat(live.isChatEnabled()).isTrue();
    assertThat(live.getLiveUuid()).isNotNull();
    assertThat(live.getCreatedAt()).isEqualTo(NOW);
    assertThat(live.getId()).isNull();
  }

  @Test
  void 필수_값이_null이면_생성할_수_없다() {
    LocalDateTime start = NOW.plusHours(1);
    LocalDateTime end = NOW.plusHours(2);

    assertThatThrownBy(() -> Live.create(null, SELLER, 1L, null, "첫 방송", null, start, end, NOW))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Live.create(UUID.randomUUID(), null, 1L, null, "첫 방송", null,
        start, end, NOW))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Live.create(UUID.randomUUID(), SELLER, null, null, "첫 방송", null,
        start, end, NOW))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void 예정_상태에서_시작하면_진행중이_되고_시작_시각이_기록된다() {
    Live live = createLive();

    live.start(NOW.plusMinutes(1));

    assertThat(live.getStatus()).isEqualTo(LiveStatus.RUNNING);
    assertThat(live.getStartedAt()).isEqualTo(NOW.plusMinutes(1));
  }

  @Test
  void 진행중에_일시정지하고_다시_재개할_수_있다() {
    Live live = createLive();
    live.start(NOW);

    live.pause(NOW.plusMinutes(1));
    assertThat(live.getStatus()).isEqualTo(LiveStatus.PAUSED);

    live.resume(NOW.plusMinutes(2));
    assertThat(live.getStatus()).isEqualTo(LiveStatus.RUNNING);
  }

  @Test
  void 진행중에_종료하면_종료_정보가_기록된다() {
    Live live = createLive();
    live.start(NOW);

    live.end(NOW.plusMinutes(30), LiveEndReason.SELLER_ENDED, SELLER);

    assertThat(live.getStatus()).isEqualTo(LiveStatus.TERMINATED);
    assertThat(live.getEndedAt()).isEqualTo(NOW.plusMinutes(30));
    assertThat(live.getEndReason()).isEqualTo(LiveEndReason.SELLER_ENDED);
    assertThat(live.getEndedBy()).isEqualTo(SELLER);
  }

  @Test
  void 예정_상태에서_취소하면_취소_정보가_기록된다() {
    Live live = createLive();

    live.cancel(NOW.plusMinutes(5), LiveCancelReason.SELLER_CANCELED, SELLER);

    assertThat(live.getStatus()).isEqualTo(LiveStatus.CANCELED);
    assertThat(live.getCanceledAt()).isEqualTo(NOW.plusMinutes(5));
    assertThat(live.getCancelReason()).isEqualTo(LiveCancelReason.SELLER_CANCELED);
    assertThat(live.getCanceledBy()).isEqualTo(SELLER);
  }

  @Test
  void 허용되지_않은_상태_전이는_예외가_발생하고_상태가_바뀌지_않는다() {
    Live live = createLive();

    assertThatThrownBy(() -> live.pause(NOW))
        .isInstanceOf(BusinessException.class);
    assertThatThrownBy(() -> live.end(NOW, LiveEndReason.SELLER_ENDED, SELLER))
        .isInstanceOf(BusinessException.class);
    assertThat(live.getStatus()).isEqualTo(LiveStatus.CREATED);
  }

  @Test
  void 진행중인_방송은_취소할_수_없다() {
    Live live = createLive();
    live.start(NOW);

    assertThatThrownBy(() -> live.cancel(NOW, LiveCancelReason.SELLER_CANCELED, SELLER))
        .isInstanceOf(BusinessException.class);
    assertThat(live.getStatus()).isEqualTo(LiveStatus.RUNNING);
  }

  @Test
  void 일시정지_상태에서도_방송을_종료할_수_있다() {
    Live live = createLive();
    live.start(NOW);
    live.pause(NOW.plusMinutes(1));

    live.end(NOW.plusMinutes(30), LiveEndReason.SELLER_ENDED, SELLER);

    assertThat(live.getStatus()).isEqualTo(LiveStatus.TERMINATED);
  }

  @Test
  void 상태_변경_시각이_null이면_예외가_발생하고_상태가_바뀌지_않는다() {
    Live live = createLive();

    assertThatThrownBy(() -> live.start(null))
        .isInstanceOf(NullPointerException.class);
    assertThat(live.getStatus()).isEqualTo(LiveStatus.CREATED);
    assertThat(live.getStartedAt()).isNull();
  }

  private Live createLive() {
    return Live.create(UUID.randomUUID(), SELLER, 1L, null,
        "첫 방송", "설명", NOW.plusHours(1), NOW.plusHours(2), NOW);
  }
}
