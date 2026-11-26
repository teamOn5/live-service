package com.teamon5.livepick.live.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.msa.common.core.error.BusinessException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class LiveMediaTest {

  private static final LocalDateTime NOW = LocalDateTime.of(2026, 12, 1, 9, 0);
  private static final long MB = 1024L * 1024;

  @Test
  void 이미지_크기가_10MB_이하이면_생성되고_uuid가_발급된다() {
    LiveMedia media = LiveMedia.create(LiveMediaType.IMAGE, "/img", "a.png", "a.png",
        "image/png", 10 * MB, NOW);

    assertThat(media.getMediaUuid()).isNotNull();
    assertThat(media.getMediaType()).isEqualTo(LiveMediaType.IMAGE);
    assertThat(media.getFileSize()).isEqualTo(10 * MB);
  }

  @Test
  void 이미지_크기가_10MB를_넘으면_예외가_발생한다() {
    assertThatThrownBy(() -> LiveMedia.create(LiveMediaType.IMAGE, "/img", "a.png", "a.png",
        "image/png", 10 * MB + 1, NOW))
        .isInstanceOf(BusinessException.class);
  }

  @Test
  void 이미지는_파일_크기가_없으면_생성할_수_없다() {
    assertThatThrownBy(() -> LiveMedia.create(LiveMediaType.IMAGE, "/img", "a.png", "a.png",
        "image/png", null, NOW))
        .isInstanceOf(BusinessException.class);
  }

  @Test
  void 동영상은_크기_제한을_적용하지_않는다() {
    LiveMedia media = LiveMedia.create(LiveMediaType.VIDEO, "/vid", "v.mp4", "v.mp4",
        "video/mp4", 500 * MB, NOW);

    assertThat(media.getMediaType()).isEqualTo(LiveMediaType.VIDEO);
  }

  @Test
  void 파일_크기가_음수이면_예외가_발생한다() {
    assertThatThrownBy(() -> LiveMedia.create(LiveMediaType.VIDEO, "/vid", "v.mp4", "v.mp4",
        "video/mp4", -1L, NOW))
        .isInstanceOf(BusinessException.class);
  }

  @Test
  void 저장_이름이_null이면_생성할_수_없다() {
    assertThatThrownBy(() -> LiveMedia.create(LiveMediaType.IMAGE, "/img", null, "a.png",
        "image/png", 1L, NOW))
        .isInstanceOf(NullPointerException.class);
  }
}
