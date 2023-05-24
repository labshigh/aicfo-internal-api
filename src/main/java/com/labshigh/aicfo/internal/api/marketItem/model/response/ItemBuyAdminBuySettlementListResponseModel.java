package com.labshigh.aicfo.internal.api.marketItem.model.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemBuyAdminBuySettlementListResponseModel {

  private long itemUid;
  private String email;
  private BigDecimal price;
  private String priceStr;
  private BigDecimal interestPrice;
  private String interestPriceStr;
  private BigDecimal referrerPrice;
  private String referrerPriceStr;
  private String totalPriceStr;

}
