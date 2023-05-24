package com.labshigh.aicfo.internal.api.member.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSendVerifyEmailRequestModel {

  private long memberUid;
  private String email;
  private long memberWalletWithdrawalUid;
}
