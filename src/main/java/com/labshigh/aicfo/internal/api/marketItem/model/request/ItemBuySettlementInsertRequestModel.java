package com.labshigh.aicfo.internal.api.marketItem.model.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemBuySettlementInsertRequestModel {

  private long memberUid;
  private String userId;
  private long uid;

  private long itemBuyUid;

  private SettlementType settlementType;

  private BigDecimal price;


  public enum SettlementType {
    DEPOSIT,
    WITHDRAWAL
  }

}
