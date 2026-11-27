package com.teamon5.livepick.livecoupon.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.msa.common.core.error.BusinessException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class LiveCouponTest {

  private static final LocalDateTime NOW = LocalDateTime.of(2026, 12, 1, 9, 0);
  private static final UUID LIVE_UUID = UUID.randomUUID();

  @Test
  void 생성하면_쿠폰과_공개_기준이_기록되고_공개_시각은_비어있다() {
    UUID coupon = UUID.randomUUID();

    LiveCoupon liveCoupon = new LiveCoupon(LIVE_UUID, coupon, 10, 2, NOW);

    assertThat(liveCoupon.getLiveUuid()).isEqualTo(LIVE_UUID);
    assertThat(liveCoupon.getCouponId()).isEqualTo(coupon);
    assertThat(liveCoupon.getUnlockViewerCount()).isEqualTo(10);
    assertThat(liveCoupon.getDisplayOrder()).isEqualTo(2);
    assertThat(liveCoupon.getUnlockedAt()).isNull();
  }

  @Test
  void 공개_기준이_음수이면_예외가_발생한다() {
    assertThatThrownBy(() -> new LiveCoupon(LIVE_UUID, UUID.randomUUID(), -1, 0, NOW))
        .isInstanceOf(BusinessException.class);
  }

  @Test
  void 방송_아이디가_null이면_생성할_수_없다() {
    assertThatThrownBy(() -> new LiveCoupon(null, UUID.randomUUID(), 0, 0, NOW))
        .isInstanceOf(NullPointerException.class);
  }
}