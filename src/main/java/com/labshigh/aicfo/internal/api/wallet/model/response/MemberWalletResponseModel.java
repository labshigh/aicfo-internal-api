package com.labshigh.aicfo.internal.api.wallet.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class MemberWalletResponseModel {

  private Long coinId;
  private Long tokenId;
  private String name;
  private String address;
  private Long accountId;
  private Long memberUid;

  private BigDecimal mEth;
  private BigDecimal balance;

  private String userId;
  private String referrerCode;
  private String referrer;

  private BigDecimal migEth;
  private BigDecimal migStaking;

  private String qrCode;



}
