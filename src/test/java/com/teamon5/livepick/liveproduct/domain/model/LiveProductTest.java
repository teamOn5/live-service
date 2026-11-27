package com.teamon5.livepick.liveproduct.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.msa.common.core.error.BusinessException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class LiveProductTest {

  private static final LocalDateTime NOW = LocalDateTime.of(2026, 12, 1, 9, 0);
  private static final UUID LIVE_UUID = UUID.randomUUID();

  @Test
  void 생성하면_상품과_옵션과_노출_순서가_기록된다() {
    UUID product = UUID.randomUUID();
    UUID option = UUID.randomUUID();

    LiveProduct liveProduct = new LiveProduct(LIVE_UUID, product, option, "블랙 M", 1, NOW);

    assertThat(liveProduct.getLiveUuid()).isEqualTo(LIVE_UUID);
    assertThat(liveProduct.getProductId()).isEqualTo(product);
    assertThat(liveProduct.getProductOptionId()).isEqualTo(option);
    assertThat(liveProduct.getOptionName()).isEqualTo("블랙 M");
    assertThat(liveProduct.getDisplayOrder()).isEqualTo(1);
  }

  @Test
  void 옵션명이_비어있으면_예외가_발생한다() {
    assertThatThrownBy(() ->
        new LiveProduct(LIVE_UUID, UUID.randomUUID(), UUID.randomUUID(), " ", 0, NOW))
        .isInstanceOf(BusinessException.class);
  }

  @Test
  void 노출_순서가_음수이면_예외가_발생한다() {
    assertThatThrownBy(() ->
        new LiveProduct(LIVE_UUID, UUID.randomUUID(), UUID.randomUUID(), "블랙", -1, NOW))
        .isInstanceOf(BusinessException.class);
  }

  @Test
  void 방송_아이디가_null이면_생성할_수_없다() {
    assertThatThrownBy(() ->
        new LiveProduct(null, UUID.randomUUID(), UUID.randomUUID(), "블랙", 0, NOW))
        .isInstanceOf(NullPointerException.class);
  }
}