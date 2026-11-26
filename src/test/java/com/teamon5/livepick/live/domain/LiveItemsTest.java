package com.teamon5.livepick.live.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.msa.common.core.error.BusinessException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;

class LiveItemsTest {

  private static final LocalDateTime NOW = LocalDateTime.of(2026, 12, 1, 9, 0);

  @Test
  void 해시태그는_최대_5개까지_등록할_수_있다() {
    Live live = createLive();
    for (int i = 0; i < 5; i++) {
      live.addHashtag("태그" + i);
    }

    assertThat(live.getHashtags()).hasSize(5);
    assertError(() -> live.addHashtag("초과"), LiveErrorCode.ITEM_LIMIT_EXCEEDED);
  }

  @Test
  void 같은_해시태그는_중복_등록할_수_없다() {
    Live live = createLive();
    live.addHashtag("여름");

    assertError(() -> live.addHashtag("여름"), LiveErrorCode.DUPLICATE_LIVE_ITEM);
  }

  @Test
  void 해시태그가_10자를_넘으면_예외가_발생한다() {
    Live live = createLive();

    assertError(() -> live.addHashtag("가".repeat(11)), LiveErrorCode.INVALID_HASHTAG);
  }

  @Test
  void 호스트는_최대_5명까지_등록할_수_있다() {
    Live live = createLive();
    for (int i = 0; i < 5; i++) {
      live.addHost("호스트" + i, null, i);
    }

    assertThat(live.getHosts()).hasSize(5);
    assertError(() -> live.addHost("초과", null, 5), LiveErrorCode.ITEM_LIMIT_EXCEEDED);
  }

  @Test
  void 상품_옵션은_최대_5개까지_등록할_수_있다() {
    Live live = createLive();
    for (int i = 0; i < 5; i++) {
      live.addProduct(UUID.randomUUID(), UUID.randomUUID(), "옵션" + i, i, NOW);
    }

    assertThat(live.getProducts()).hasSize(5);
    assertError(() -> live.addProduct(UUID.randomUUID(), UUID.randomUUID(), "초과", 5, NOW),
        LiveErrorCode.ITEM_LIMIT_EXCEEDED);
  }

  @Test
  void 같은_상품_옵션은_중복_등록할_수_없다() {
    Live live = createLive();
    UUID option = UUID.randomUUID();
    live.addProduct(UUID.randomUUID(), option, "옵션", 0, NOW);

    assertError(() -> live.addProduct(UUID.randomUUID(), option, "옵션", 1, NOW),
        LiveErrorCode.DUPLICATE_LIVE_ITEM);
  }

  @Test
  void 쿠폰은_최대_5개까지_연결할_수_있고_같은_쿠폰은_중복_연결할_수_없다() {
    Live live = createLive();
    UUID first = UUID.randomUUID();
    live.addCoupon(first, 0, 0, NOW);

    assertError(() -> live.addCoupon(first, 0, 1, NOW), LiveErrorCode.DUPLICATE_LIVE_ITEM);
    for (int i = 1; i < 5; i++) {
      live.addCoupon(UUID.randomUUID(), 0, i, NOW);
    }
    assertThat(live.getCoupons()).hasSize(5);
    assertError(() -> live.addCoupon(UUID.randomUUID(), 0, 5, NOW),
        LiveErrorCode.ITEM_LIMIT_EXCEEDED);
  }

  @Test
  void 공지는_여러_개_등록되고_내용이_50자를_넘으면_예외가_발생한다() {
    Live live = createLive();

    live.addNotice("첫 공지", UUID.randomUUID(), NOW);
    live.addNotice("둘째 공지", UUID.randomUUID(), NOW);

    assertThat(live.getNotices()).hasSize(2);
    assertError(() -> live.addNotice("가".repeat(51), UUID.randomUUID(), NOW),
        LiveErrorCode.INVALID_NOTICE_CONTENT);
  }

  @Test
  void 자식_목록은_외부에서_수정할_수_없다() {
    Live live = createLive();
    live.addHashtag("여름");

    assertThatThrownBy(() -> live.getHashtags().clear())
        .isInstanceOf(UnsupportedOperationException.class);
  }

  private static void assertError(ThrowingCallable call, LiveErrorCode expected) {
    assertThatThrownBy(call)
        .isInstanceOf(BusinessException.class)
        .extracting(e -> ((BusinessException) e).getErrorCode())
        .isEqualTo(expected);
  }

  private static Live createLive() {
    return Live.create(UUID.randomUUID(), UUID.randomUUID(), 1L, null,
        "첫 방송", null, NOW.plusHours(1), NOW.plusHours(2), NOW);
  }
}
