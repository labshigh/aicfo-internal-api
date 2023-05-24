package com.labshigh.aicfo.internal.api.marketItem.model.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemBuyInsertRequestModel {

  private long memberUid;
  private String userId;
  private long itemUid;
  private long walletId;
  private long marketItemUid;
  private BigDecimal price;
  private PriceUnit priceUnit;

  //private int itemKind;

  public enum PriceUnit {
    ETH,
    mETH
  }
}


