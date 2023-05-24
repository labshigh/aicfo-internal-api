package com.labshigh.aicfo.internal.api.marketItem.model.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemBuyCurPriceInfoResponseModel {

  private BigDecimal totalEthPrice;
  private BigDecimal totalMethPrice;
  private BigDecimal totalInterestPrice;
  private BigDecimal depositPrice;
  private BigDecimal waitPrice;
  private String totalEthPriceStr;
  private String totalMethPriceStr;
  private String totalInterestPriceStr;
  private String waitTotalPriceStr;

}
