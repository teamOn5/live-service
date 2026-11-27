package com.teamon5.livepick.livestream.domain.model;

import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LiveStream {

  private Long id;
  private final UUID liveUuid;
  private final String streamKey;
  private final String ingestUrl;
  private final String playbackUrl;
  private final String chatChannel;

  private LiveStream(UUID liveUuid, String streamKey, String ingestUrl, String playbackUrl,
      String chatChannel) {
    this.liveUuid = Objects.requireNonNull(liveUuid);
    this.streamKey = streamKey;
    this.ingestUrl = ingestUrl;
    this.playbackUrl = playbackUrl;
    this.chatChannel = chatChannel;
  }

  public static LiveStream create(UUID liveUuid, String streamKey, String ingestUrl,
      String playbackUrl, String chatChannel) {
    return new LiveStream(liveUuid, streamKey, ingestUrl, playbackUrl, chatChannel);
  }
}
