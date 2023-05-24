package com.labshigh.aicfo.internal.api.member.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSendSmsVerifyRequestModel {

  private long memberUid;
  private String nationalCode;
  private String phoneNumber;
}
