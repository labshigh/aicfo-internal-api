package com.labshigh.aicfo.internal.api.staking.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminWalletInsertRequestModel {

  private String walletAddress;
  private String description;
  private long adminUid;

}
