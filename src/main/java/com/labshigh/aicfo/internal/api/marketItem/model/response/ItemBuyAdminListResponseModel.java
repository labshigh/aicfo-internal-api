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
public class ItemBuyAdminListResponseModel {

  private long uid;
  private String round;
  private int month;
  private int countBuy;

  private String closeRound;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime startAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime endAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime requestEndAt;
  private BigDecimal buyEthTotalPrice;
  private BigDecimal buyMethTotalPrice;
  private String buyEthTotalPriceStr;
  private String buyMethTotalPriceStr;
  private int countEthBuy;
  private int countMethBuy;
  private BigDecimal buyTotalPrice;
  private String buyTotalPriceStr;
  private String totalPriceStr;
  private BigDecimal interestPrice;
  private String interestPriceStr;
  private String countAutoProc; // 자동 진행 건수
  private int countRequestWithdrawal; //해지 요청 건수
  private String countRequest;
  private String countDeposit;
  private String countTotal;
  private String countWithdrawal;
}
