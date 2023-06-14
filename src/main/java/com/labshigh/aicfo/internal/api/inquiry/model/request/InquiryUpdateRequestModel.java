package com.labshigh.aicfo.internal.api.inquiry.model.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryUpdateRequestModel {

  private long uid;
  private String inquiryTime;
  private long memberUid;
}
