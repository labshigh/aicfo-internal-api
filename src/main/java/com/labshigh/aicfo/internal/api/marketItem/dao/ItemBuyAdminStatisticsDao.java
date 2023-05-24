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
public class ItemBuyAdminStatisticsDao {

  private long uid;
  private boolean procFlag; //현재 진행중인 회차 여부
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
  private int countEthBuy;
  private int countMethBuy;
  private BigDecimal interestPrice;
  private String countAutoProc; // 자동 진행 건수
  private int countRequestWithdrawal; //해지 요청 건수

  private String countTotal;

  private String countRequest;
  private String countDeposit;
  private String countWithdrawal;
  private BigDecimal withdrawalPrice;
  private BigDecimal referrerPrice;


}
