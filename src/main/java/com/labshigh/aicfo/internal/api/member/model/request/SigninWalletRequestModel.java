package com.labshigh.aicfo.internal.api.member.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SigninWalletRequestModel {

  private Long walletId;
  private String referrerCode;
  private Long memberUid;
}