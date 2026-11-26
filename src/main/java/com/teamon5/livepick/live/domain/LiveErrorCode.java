package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum LiveErrorCode implements ErrorCode {

  INVALID_LIVE_TITLE("LIVE4001", HttpStatus.BAD_REQUEST, "방송 제목은 1자 이상 20자 이하여야 합니다."),
  INVALID_LIVE_DESCRIPTION("LIVE4002", HttpStatus.BAD_REQUEST, "방송 설명은 100자 이하여야 합니다."),
  INVALID_LIVE_SCHEDULE("LIVE4003", HttpStatus.BAD_REQUEST,
      "예정 종료 시각은 예정 시작 시각보다 10분 이상 뒤여야 합니다."),
  INVALID_MEDIA_FILE_SIZE("LIVE4004", HttpStatus.BAD_REQUEST, "이미지는 10MB 이하여야 합니다."),
  INVALID_HASHTAG("LIVE4005", HttpStatus.BAD_REQUEST, "해시태그는 1자 이상 10자 이하여야 합니다."),
  INVALID_HOST_NAME("LIVE4006", HttpStatus.BAD_REQUEST, "호스트 이름은 1자 이상 10자 이하여야 합니다."),
  INVALID_NOTICE_CONTENT("LIVE4007", HttpStatus.BAD_REQUEST, "공지는 1자 이상 50자 이하여야 합니다."),
  INVALID_OPTION_NAME("LIVE4008", HttpStatus.BAD_REQUEST, "옵션명은 1자 이상 255자 이하여야 합니다."),
  ITEM_LIMIT_EXCEEDED("LIVE4009", HttpStatus.BAD_REQUEST, "등록할 수 있는 개수를 초과했습니다."),
  DUPLICATE_LIVE_ITEM("LIVE4090", HttpStatus.CONFLICT, "이미 등록된 항목입니다."),
  INVALID_LIVE_STATUS_TRANSITION("LIVE4091", HttpStatus.CONFLICT,
      "현재 방송 상태에서는 요청한 작업을 할 수 없습니다.");

  private final String code;
  private final HttpStatus httpStatus;
  private final String message;

  LiveErrorCode(String code, HttpStatus httpStatus, String message) {
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
