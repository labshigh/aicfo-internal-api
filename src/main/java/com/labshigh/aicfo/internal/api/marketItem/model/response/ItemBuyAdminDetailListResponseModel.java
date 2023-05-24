package com.labshigh.aicfo.internal.api.marketItem.model.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemBuyAdminDetailListResponseModel {

  private long itemUid;
  private long memberUid;
  private String email;
  private BigDecimal totalPrice;
  private String totalPriceStr;
  private String requestCount;
  private boolean autoProgressFlag;
  private String status;

}
