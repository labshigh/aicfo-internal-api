package com.labshigh.aicfo.internal.api.member.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberVerifySmsRequestModel {

  private long memberUid;
  private String nationalCode;
  private String phoneNumber;
  private String verifyCode;
}