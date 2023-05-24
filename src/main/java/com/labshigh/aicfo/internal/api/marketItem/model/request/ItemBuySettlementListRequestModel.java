package com.labshigh.aicfo.internal.api.marketItem.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemBuySettlementListRequestModel {

  private long uid;
  private long memberUid;
  private long itemUid;

  private String userId;

}
