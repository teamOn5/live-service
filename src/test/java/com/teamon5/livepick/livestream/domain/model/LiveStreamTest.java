package com.teamon5.livepick.livestream.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class LiveStreamTest {

  private static final UUID LIVE_UUID = UUID.randomUUID();

  @Test
  void 생성하면_송출_정보가_그대로_기록된다() {
    LiveStream stream =
        LiveStream.create(LIVE_UUID, "key", "rtmps://ingest", "https://play", "chat-1");

    assertThat(stream.getLiveUuid()).isEqualTo(LIVE_UUID);
    assertThat(stream.getStreamKey()).isEqualTo("key");
    assertThat(stream.getIngestUrl()).isEqualTo("rtmps://ingest");
    assertThat(stream.getPlaybackUrl()).isEqualTo("https://play");
    assertThat(stream.getChatChannel()).isEqualTo("chat-1");
  }

  @Test
  void 송출_정보는_비어있어도_생성된다() {
    LiveStream stream = LiveStream.create(LIVE_UUID, null, null, null, null);

    assertThat(stream.getStreamKey()).isNull();
    assertThat(stream.getPlaybackUrl()).isNull();
  }

  @Test
  void 방송_ID가_null이면_생성할_수_없다() {
    assertThatThrownBy(() -> LiveStream.create(null, "key", null, null, null))
        .isInstanceOf(NullPointerException.class);
  }
}
