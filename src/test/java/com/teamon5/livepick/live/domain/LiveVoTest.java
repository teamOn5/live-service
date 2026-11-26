package com.teamon5.livepick.live.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.msa.common.core.error.BusinessException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class LiveVoTest {

  @Test
  void 제목이_1자_이상_20자_이하이면_생성된다() {
    assertThat(new LiveTitle("a").value()).isEqualTo("a");
    assertThat(new LiveTitle("가".repeat(20)).value()).hasSize(20);
  }

  @Test
  void 제목이_비어있거나_20자를_넘으면_예외가_발생한다() {
    assertThatThrownBy(() -> new LiveTitle(null))
        .isInstanceOf(BusinessException.class)
        .extracting(e -> ((BusinessException) e).getErrorCode())
        .isEqualTo(LiveErrorCode.INVALID_LIVE_TITLE);
    assertThatThrownBy(() -> new LiveTitle(" "))
        .isInstanceOf(BusinessException.class);
    assertThatThrownBy(() -> new LiveTitle("가".repeat(21)))
        .isInstanceOf(BusinessException.class);
  }

  @Test
  void 설명이_100자_이하이면_생성되고_100자를_넘으면_예외가_발생한다() {
    assertThat(new LiveDescription("a".repeat(100)).value()).hasSize(100);
    assertThatThrownBy(() -> new LiveDescription("a".repeat(101)))
        .isInstanceOf(BusinessException.class);
  }

  @Test
  void 종료가_시작보다_10분_이상_뒤이면_일정이_생성된다() {
    LocalDateTime start = LocalDateTime.of(2026, 12, 1, 10, 0);

    LiveSchedule schedule = new LiveSchedule(start, start.plusMinutes(10));

    assertThat(schedule.scheduledEndAt()).isEqualTo(start.plusMinutes(10));
  }

  @Test
  void 종료가_시작보다_10분_미만이거나_null이면_예외가_발생한다() {
    LocalDateTime start = LocalDateTime.of(2026, 12, 1, 10, 0);

    assertThatThrownBy(() -> new LiveSchedule(start, start.plusMinutes(9)))
        .isInstanceOf(BusinessException.class);
    assertThatThrownBy(() -> new LiveSchedule(start, null))
        .isInstanceOf(BusinessException.class);
    assertThatThrownBy(() -> new LiveSchedule(null, start))
        .isInstanceOf(BusinessException.class);
  }
}
