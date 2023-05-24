package com.labshigh.aicfo.internal.api.marketItem.model.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MarketItemMyReferrerResponseModel {

  private String referrerCode;
  private BigDecimal methPrice;
  private String methPriceStr;
  private BigDecimal totalPrice;
  private String totalPriceStr;
  private String email;
  private String interest;
  private BigDecimal interestPrice;
  private String interestPriceStr;
  private boolean pointAbleFlag;
}
