package com.labshigh.aicfo.internal.api.totp.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TotpActivateRequestModel {

  private long memberUid;
  private String code;
}
