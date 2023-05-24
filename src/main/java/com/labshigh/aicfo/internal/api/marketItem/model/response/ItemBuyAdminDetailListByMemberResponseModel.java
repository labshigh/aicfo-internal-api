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
public class ItemBuyAdminDetailListByMemberResponseModel {

  private long uid;
  private long itemUid;
  private String email;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  private String totalPriceStr;
  private BigDecimal price;
  private String priceStr;
  private BigDecimal requestPrice;
  private String requestPriceStr;
  private BigDecimal depositPrice;
  private String depositPriceStr;
  private BigDecimal withdrawalPrice;
  private String withdrawalPriceStr;
  private BigDecimal interestPrice;
  private String interestPriceStr;
  private String round;
  private String prevRound;
  private String autoRound;
  private boolean autoProgressFlag;

}
