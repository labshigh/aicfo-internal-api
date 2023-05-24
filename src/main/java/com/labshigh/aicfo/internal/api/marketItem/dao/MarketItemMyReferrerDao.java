package com.labshigh.aicfo.internal.api.marketItem.dao;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketItemMyReferrerDao {

  private String referrerCode;
  private BigDecimal methPrice;
  private BigDecimal totalPrice;
  private String email;
  private String interest;
  private BigDecimal interestPrice;
  private boolean pointAbleFlag;

}
