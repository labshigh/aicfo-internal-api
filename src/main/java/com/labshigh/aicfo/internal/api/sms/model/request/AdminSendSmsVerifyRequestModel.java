package com.labshigh.aicfo.internal.api.sms.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSendSmsVerifyRequestModel {

  private long adminUid;
  private String menuId;
//  private String nationalCode;
//  private String mobile;
}
