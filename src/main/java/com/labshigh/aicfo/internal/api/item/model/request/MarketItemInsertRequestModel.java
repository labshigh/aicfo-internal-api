package com.labshigh.aicfo.internal.api.item.model.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketItemInsertRequestModel {

  private long itemUid;
  private long adminWalletUid;

  private String transactionHash;
  private BigDecimal price;


}
