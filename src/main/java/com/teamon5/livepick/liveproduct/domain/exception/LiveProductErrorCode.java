package com.teamon5.livepick.liveproduct.domain.exception;

import com.msa.common.core.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum LiveProductErrorCode implements ErrorCode {

  INVALID_OPTION_NAME("LIVE4008", HttpStatus.BAD_REQUEST, "옵션명은 1자 이상 255자 이하여야 합니다.");

  private final String code;
  private final HttpStatus httpStatus;
  private final String message;

  LiveProductErrorCode(String code, HttpStatus httpStatus, String message) {
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
