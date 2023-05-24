package com.labshigh.aicfo.internal.api.marketItem.model.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfitModel {

  public ProfitModel() {
    price = BigDecimal.ZERO;
    krwPrice = BigDecimal.ZERO;
    interestPrice = BigDecimal.ZERO;
    krwInterestPrice = BigDecimal.ZERO;
    interestDayPrice = BigDecimal.ZERO;
    krwInterestDayPrice = BigDecimal.ZERO;
  }

  private BigDecimal price;
  private BigDecimal krwPrice;
  private BigDecimal interestPrice;
  private BigDecimal krwInterestPrice;
  private BigDecimal interestDayPrice;
  private BigDecimal krwInterestDayPrice;

}
