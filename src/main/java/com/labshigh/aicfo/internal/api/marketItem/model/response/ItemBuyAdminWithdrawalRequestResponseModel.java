package com.labshigh.aicfo.internal.api.marketItem.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemBuyAdminWithdrawalRequestResponseModel {

  private long itemUid;
  private long memberUid;
  private String email;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime withdrawalRequestAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime withdrawalApprovalAt;
  private String totalPriceStr;
  private BigDecimal price;
  private String priceStr;
  private BigDecimal interestPrice;
  private String interestPriceStr;
  private String round;
  private boolean autoProgressFlag;
  private boolean withdrawalCompletedFlag;
  private long itemBuySettlementUid;
  private boolean withdrawalApprovalFlag;
  private BigDecimal withdrawalPrice;
  private String withdrawalPriceStr;
  private int autoCount;
}
