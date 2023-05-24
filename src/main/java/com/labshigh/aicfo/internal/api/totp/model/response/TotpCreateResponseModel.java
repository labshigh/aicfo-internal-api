package com.labshigh.aicfo.internal.api.totp.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class TotpCreateResponseModel {

  private String barcode;
  private String uri;
  private String secret;
}

