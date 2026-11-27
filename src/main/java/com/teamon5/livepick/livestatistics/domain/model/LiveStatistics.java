package com.teamon5.livepick.livestatistics.domain.model;

import com.msa.common.core.error.BusinessException;
import com.msa.common.core.error.CommonErrorCode;

record LiveStatistics(int peakViewerCount, int uniqueViewerCount, int chatCount) {

  LiveStatistics {
    if (peakViewerCount < 0 || uniqueViewerCount < 0 || chatCount < 0) {
      throw new BusinessException(CommonErrorCode.INVALID_INPUT);
    }
  }
}
