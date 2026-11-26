package com.teamon5.livepick.live.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.msa.common.core.error.BusinessException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class LiveVoTest {

  private static final LocalDateTime START = LocalDateTime.of(2026, 12, 1, 10, 0);

  @Test
  void 제목이_1자_이상_20자_이하이면_생성된다() {
    assertThat(create("a", null, START, START.plusMinutes(10)).getTitle()).isEqualTo("a");
    assertThat(create("가".repeat(20), null, START, START.plusMinutes(10)).getTitle())
        .hasSize(20);
  }

  @Test
  void 제목이_비어있거나_20자를_넘으면_예외가_발생한다() {
    assertThatThrownBy(() -> create(null, null, START, START.plusMinutes(10)))
        .isInstanceOf(BusinessException.class);
    assertThatThrownBy(() -> create(" ", null, START, START.plusMinutes(10)))
        .isInstanceOf(BusinessException.class);
    assertThatThrownBy(() -> create("가".repeat(21), null, START, START.plusMinutes(10)))
        .isInstanceOf(BusinessException.class);
  }

  @Test
  void 설명이_100자_이하이면_생성되고_100자를_넘으면_예외가_발생한다() {
    assertThat(create("제목", "a".repeat(100), START, START.plusMinutes(10)).getDescription())
        .hasSize(100);
    assertThatThrownBy(() -> create("제목", "a".repeat(101), START, START.plusMinutes(10)))
        .isInstanceOf(BusinessException.class);
  }

  @Test
  void 설명이_없으면_null로_조회된다() {
    assertThat(create("제목", null, START, START.plusMinutes(10)).getDescription()).isNull();
  }

  @Test
  void 종료가_시작보다_10분_이상_뒤이면_생성된다() {
    Live live = create("제목", null, START, START.plusMinutes(10));

    assertThat(live.getScheduledEndAt()).isEqualTo(START.plusMinutes(10));
  }

  @Test
  void 종료가_시작보다_10분_미만이거나_null이면_예외가_발생한다() {
    assertThatThrownBy(() -> create("제목", null, START, START.plusMinutes(9)))
        .isInstanceOf(BusinessException.class);
    assertThatThrownBy(() -> create("제목", null, START, null))
        .isInstanceOf(BusinessException.class);
    assertThatThrownBy(() -> create("제목", null, null, START))
        .isInstanceOf(BusinessException.class);
  }

  private static Live create(String title, String description, LocalDateTime start,
      LocalDateTime end) {
    return Live.create(UUID.randomUUID(), UUID.randomUUID(), 1L, null,
        title, description, start, end, START);
  }
}
