package com.labshigh.aicfo.internal.api.wallet.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class WalletBalanceRequestModel {

  // 사용자 아이디
  private Long memberId;
  private String userId;
  // 토큰 고유 번호
  private Long tokenId;

}
