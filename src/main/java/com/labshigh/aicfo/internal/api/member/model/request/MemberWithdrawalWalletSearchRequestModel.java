package com.labshigh.aicfo.internal.api.member.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberWithdrawalWalletSearchRequestModel {

  private String keyword;
  private Long memberUid;

}
