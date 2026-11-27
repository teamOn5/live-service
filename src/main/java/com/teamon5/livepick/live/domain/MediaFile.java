package com.teamon5.livepick.live.domain;

import com.msa.common.core.error.BusinessException;
import com.msa.common.core.error.CommonErrorCode;

record MediaFile(String fileName, String fileType, Long fileSize) {

  MediaFile {
    if (fileSize != null && fileSize < 0) {
      throw new BusinessException(CommonErrorCode.INVALID_INPUT);
    }
  }
}
