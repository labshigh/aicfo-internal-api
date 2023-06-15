package com.labshigh.aicfo.internal.api.inquiry.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InquiryResponseModel {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private Boolean usedFlag;

  private long inquiryCommonCodeUid;
  private String inquiryCommonCodeName;
  private String inquiryTime;
  private long cfoUid;
  private long memberUid;

}
