package com.labshigh.aicfo.internal.api.totp.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TotpCreateRequestModel {

  private long memberUid;
  private boolean usedFlag = false;
}
