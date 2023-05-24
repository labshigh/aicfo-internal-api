package com.labshigh.aicfo.internal.api.wallet.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberWalletTokenModel {

  private Long tokenId;
  private String name;
  private String symbol;
  private String type;
  private Long decimalUnit;
  private String protocol;
  private String coinId;
  private String enabled;
  private String logo;
  private String enableFee;
  private int zorder;
  private String primaryColor;

  private String secondColor;

  private Double feePercent;
  private Double minFeeAmount;
  private Double innerTxFeePercent;
  private Double feeValue;
  private Long feeTokenId;
  private String feeSymbol;
  private String qrCode;

}
