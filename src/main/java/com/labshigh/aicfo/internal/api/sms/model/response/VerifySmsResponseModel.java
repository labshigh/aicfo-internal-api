package com.labshigh.aicfo.internal.api.sms.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VerifySmsResponseModel {

  private boolean smsVerifyFlag;
}