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
public class ItemBuyListResponseModel {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private Boolean usedFlag;

  private long memberUid;
  private BigDecimal price;
  private BigDecimal krwPrice;
  private BigDecimal interestPrice;
  private BigDecimal krwInterestPrice;
  private BigDecimal krwEth;

  private String priceStr;
  private String krwPriceStr;
  private String interestPriceStr;
  private String krwInterestPriceStr;

  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime startAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime endAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime requestEndAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime withdrawalRequestEndAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime curWithdrawalRequestEndAt;
  private String round;
  private String closeRound;
  private BigDecimal minPrice;
  private String interest;
  private String priceUnit;
  private String remainingDays;

  private Long itemUid;


}
