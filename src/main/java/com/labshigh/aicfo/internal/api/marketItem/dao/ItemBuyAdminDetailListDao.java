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
public class ItemBuyAdminDetailListDao {

  private long itemUid;
  private long memberUid;
  private String email;
  private BigDecimal totalPrice;
  private String requestCount;
  private boolean autoProgressFlag;
  private String status;


}
