package com.labshigh.aicfo.internal.api.admin.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSendVerifyEmailRequestModel {

  private long adminUid;
  private String email;

}
