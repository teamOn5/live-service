package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.BusinessException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LiveMedia {

  private static final long MAX_IMAGE_SIZE_BYTES = 10L * 1024 * 1024;

  private Long id;
  private final UUID mediaUuid;
  private final LiveMediaType mediaType;
  private final String fullPath;
  private final String name;
  private final String fileName;
  private final String fileType;
  private final Long fileSize;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private LiveMedia(LiveMediaType mediaType, String fullPath, String name, String fileName,
      String fileType, Long fileSize, LocalDateTime now) {
    this.mediaUuid = UUID.randomUUID();
    this.mediaType = Objects.requireNonNull(mediaType);
    this.fullPath = fullPath;
    this.name = Objects.requireNonNull(name);
    this.fileName = fileName;
    this.fileType = fileType;
    this.fileSize = fileSize;
    this.createdAt = Objects.requireNonNull(now);
    this.updatedAt = now;
  }

  public static LiveMedia create(LiveMediaType mediaType, String fullPath, String name,
      String fileName, String fileType, Long fileSize, LocalDateTime now) {
    if (mediaType == LiveMediaType.IMAGE && fileSize != null
        && fileSize > MAX_IMAGE_SIZE_BYTES) {
      throw new BusinessException(LiveErrorCode.INVALID_MEDIA_FILE_SIZE);
    }
    return new LiveMedia(mediaType, fullPath, name, fileName, fileType, fileSize, now);
  }
}
