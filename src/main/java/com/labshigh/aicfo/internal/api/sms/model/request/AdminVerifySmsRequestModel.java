package com.labshigh.aicfo.internal.api.sms.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AdminVerifySmsRequestModel {

  private long adminUid;
  private String menuId;
  private String nationalCode;
  private String mobile;
  private String verifyCode;
}