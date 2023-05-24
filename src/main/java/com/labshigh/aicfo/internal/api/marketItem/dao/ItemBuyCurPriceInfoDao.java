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
public class ItemBuyCurPriceInfoDao {

  private BigDecimal totalEthPrice;
  private BigDecimal totalMethPrice;
  private BigDecimal totalInterestPrice;
  private BigDecimal depositPrice;
  private BigDecimal waitPrice;
}
