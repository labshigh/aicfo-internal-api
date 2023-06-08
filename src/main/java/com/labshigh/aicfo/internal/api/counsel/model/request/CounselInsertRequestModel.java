package com.labshigh.aicfo.internal.api.counsel.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CounselInsertRequestModel {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private Boolean usedFlag;

  private long counselKindCommonCodeUid;
  private long counselReservationCommonCodeUid;
  private long cfoUid;
  private long memberUid;
  private long counselPaymentCommonCodeUid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime counselAt;
  private String content;
  private String phoneNumber;
  private boolean phoneVerifiedFlag;
  private long counselAtCommonCodeUid;

  List<CounselFileInsertModel> fileList;

}
