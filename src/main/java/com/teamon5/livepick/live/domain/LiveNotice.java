package com.teamon5.livepick.live.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class LiveNotice {

  private Long id;
  @Getter(AccessLevel.NONE)
  private final NoticeContent content;
  private final UUID createdBy;
  private final LocalDateTime createdAt;

  LiveNotice(NoticeContent content, UUID createdBy, LocalDateTime createdAt) {
    this.content = content;
    this.createdBy = Objects.requireNonNull(createdBy);
    this.createdAt = Objects.requireNonNull(createdAt);
  }

  public String getContent() {
    return content.value();
  }
}
