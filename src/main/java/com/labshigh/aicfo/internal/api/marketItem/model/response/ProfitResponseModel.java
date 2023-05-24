package com.labshigh.aicfo.internal.api.marketItem.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfitResponseModel {

  private String price;
  private String krwPrice;
  private String interestPrice;
  private String krwInterestPrice;
  private String interestDayPrice;
  private String krwInterestDayPrice;

}
