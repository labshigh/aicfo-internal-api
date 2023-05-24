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
public class MarketItemAdminVipTokenStakingResponseModel {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private Boolean usedFlag;
  private boolean vipFlag;
  private boolean closeFlag;
  private String round;
  private String closeRound;
  private String interest;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime startAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime endAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime requestEndAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime withdrawalRequestEndAt;
  private BigDecimal minPrice;
  private BigDecimal price;
  private BigDecimal interestPrice;
  private BigDecimal totalPrice;
  private String minPriceStr;
  private String priceStr;
  private String interestPriceStr;
  private String totalPriceStr;
  private int itemKind;
  private boolean pointAbleFlag;
  private String state;
  private Long itemUid;
  private String referrerCode;
  private int countReferrer;
  private String userId;
}
