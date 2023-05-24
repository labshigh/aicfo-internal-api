package com.labshigh.aicfo.internal.api.totp.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class TotpDetailResponseModel {

  private int digits;
  private int period;
  private String email;
  private String issuer;
  private String barcode;
  private String uri;
  private Object recoveryCodes;

}
