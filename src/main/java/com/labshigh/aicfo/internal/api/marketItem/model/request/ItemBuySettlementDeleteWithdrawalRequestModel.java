package com.labshigh.aicfo.internal.api.marketItem.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemBuySettlementDeleteWithdrawalRequestModel {

  private long uid;
  private RequestType requestType;
  private String userId;

  public enum RequestType {
    MEMBER,
    ADMIN
  }

}
