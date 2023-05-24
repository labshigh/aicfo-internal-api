package com.labshigh.aicfo.internal.api.wallet.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WalletBalanceResponseModel {

  // 사용자 아이디
  private String userId;
  // 토큰 고유 번호
  private Long tokenId;
  // balance
  private Double balance;

  private String address;

}
