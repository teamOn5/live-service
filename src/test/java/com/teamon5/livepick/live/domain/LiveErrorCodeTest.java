package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.ErrorCodeValidator;
import org.junit.jupiter.api.Test;

class LiveErrorCodeTest {

  @Test
  void 에러코드_형식과_중복이_없는지_검증한다() {
    ErrorCodeValidator.validate(LiveErrorCode.values());
  }
}
