package com.labshigh.aicfo.internal.api.inquiry.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryInsertRequestModel {

  private long inquiryCommonCodeUid;
  private String inquiryTime;
  private long cfoUid;
  private long memberUid;
}
