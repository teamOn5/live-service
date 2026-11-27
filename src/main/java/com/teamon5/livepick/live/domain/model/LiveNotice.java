package com.teamon5.livepick.live.domain.model;

import com.msa.common.core.error.BusinessException;
import com.teamon5.livepick.live.domain.exception.LiveErrorCode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LiveNotice {

  private static final int MAX_CONTENT_LENGTH = 50;

  private Long id;
  private final String content;
  private final UUID createdBy;
  private final LocalDateTime createdAt;

  LiveNotice(String content, UUID createdBy, LocalDateTime createdAt) {
    if (content == null || content.isBlank() || content.length() > MAX_CONTENT_LENGTH) {
      throw new BusinessException(LiveErrorCode.INVALID_NOTICE_CONTENT);
    }
    this.content = content;
    this.createdBy = Objects.requireNonNull(createdBy);
    this.createdAt = Objects.requireNonNull(createdAt);
  }
}
