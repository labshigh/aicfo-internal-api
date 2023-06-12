package com.labshigh.aicfo.internal.api.counsel.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselDao {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private Boolean usedFlag;

  private long counselKindCommonCodeUid;
  private String counselKindCommonCodeName;
  private long counselReservationCommonCodeUid;
  private String counselReservationCommonCodeName;
  private long cfoUid;
  private long memberUid;
  private long counselPaymentCommonCodeUid;
  private String counselPaymentCommonCodeName;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime counselAt;
  private String content;
  private String phoneNumber;
  private boolean phoneVerifiedFlag;
  private boolean completeFlag;
  private long counselAtCommonCodeUid;
  private String counselAtCommonCodeName;
  private String cfoName;
  private String career;
  private String profileUri;
  private boolean cancelFlag;


}
