package com.teamon5.livepick.media.domain.exception;

import com.msa.common.core.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MediaErrorCode implements ErrorCode {

  INVALID_MEDIA_FILE_SIZE("MEDIA4001", HttpStatus.BAD_REQUEST, "이미지는 10MB 이하여야 합니다.");

  private final String code;
  private final HttpStatus httpStatus;
  private final String message;

  MediaErrorCode(String code, HttpStatus httpStatus, String message) {
    this.code = code;
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @Override
  public HttpStatus status() {
    return httpStatus;
  }

  @Override
  public String code() {
    return code;
  }

  @Override
  public String message() {
    return message;
  }
}