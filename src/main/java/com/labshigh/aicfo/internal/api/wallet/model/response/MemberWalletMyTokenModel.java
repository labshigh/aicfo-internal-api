package com.labshigh.aicfo.internal.api.wallet.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberWalletMyTokenModel {

  private Long tokenId;
  private String name;
  private String symbol;
  private String type;
  private Long decimalUnit;
  private String protocol;
  private Long coinId;
  private String enabled;
  private String logo;
  private String userId;
  private String address;
  private double balance;
  private double priceKrw;
  private double priceUsd;
  private double lockBalance;
  private String visible;
  private String qrCode;

}
