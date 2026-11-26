package com.teamon5.livepick.live.domain;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class LiveHashtag {

  private Long id;
  @Getter(AccessLevel.NONE)
  private final Hashtag tag;

  LiveHashtag(Hashtag tag) {
    this.tag = tag;
  }

  public String getTag() {
    return tag.value();
  }
}
