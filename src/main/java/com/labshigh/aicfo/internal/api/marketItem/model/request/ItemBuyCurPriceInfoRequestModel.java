package com.labshigh.aicfo.internal.api.marketItem.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemBuyCurPriceInfoRequestModel {


  private long memberUid;
  private long walletUid;
  private String userId;

}
