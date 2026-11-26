package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.BusinessException;
import com.msa.common.core.error.CommonErrorCode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class LiveMedia {

  private static final long MAX_IMAGE_SIZE_BYTES = 10L * 1024 * 1024;

  private Long id;
  private final UUID mediaUuid;
  private final LiveMediaType mediaType;
  private final String fullPath;
  private final String name;
  @Getter(AccessLevel.NONE)
  private final MediaFile file;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private LiveMedia(LiveMediaType mediaType, String fullPath, String name, MediaFile file,
      LocalDateTime now) {
    this.mediaUuid = UUID.randomUUID();
    this.mediaType = Objects.requireNonNull(mediaType);
    this.fullPath = fullPath;
    this.name = Objects.requireNonNull(name);
    this.file = file;
    this.createdAt = Objects.requireNonNull(now);
    this.updatedAt = now;
  }

  public static LiveMedia create(LiveMediaType mediaType, String fullPath, String name,
      String fileName, String fileType, Long fileSize, LocalDateTime now) {
    MediaFile file = new MediaFile(fileName, fileType, fileSize);
    if (mediaType == LiveMediaType.IMAGE) {
      if (fileSize == null) {
        throw new BusinessException(CommonErrorCode.INVALID_INPUT);
      }
      if (fileSize > MAX_IMAGE_SIZE_BYTES) {
        throw new BusinessException(LiveErrorCode.INVALID_MEDIA_FILE_SIZE);
      }
    }
    return new LiveMedia(mediaType, fullPath, name, file, now);
  }

  public String getFileName() {
    return file.fileName();
  }

  public String getFileType() {
    return file.fileType();
  }

  public Long getFileSize() {
    return file.fileSize();
  }
}
