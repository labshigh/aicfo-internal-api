package com.labshigh.aicfo.internal.api.marketItem.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class ItemBuyAdminBuyWithdrawalRequestDao {

  private long itemUid;
  private long memberUid;

  private String email;

  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime withdrawalRequestAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime withdrawalApprovalAt;
  private BigDecimal price;
  private BigDecimal interestPrice;
  private String round;
  private long itemBuySettlementUid;
  private boolean withdrawalApprovalFlag;
  private boolean withdrawalCompletedFlag;
  private BigDecimal withdrawalPrice;
  private int autoCount;


}
