package com.labshigh.aicfo.internal.api.marketItem.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MarketItemListResponseModel {

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
  private BigDecimal totRequestEthPrice;
  private BigDecimal totRequestMEthPrice;
  private BigDecimal totInterestPrice;
  private BigDecimal totWithdrawalPrice;
  private BigDecimal totDepositPrice;
  private String totRequestEthPriceStr;
  private String totRequestMEthPriceStr;
  private String totInterestPriceStr;
  private String totWithdrawalPriceStr;
  private String totDepositPriceStr;
  private int itemKind;
  private Long autoItemUid;
  private List<ItemBuyListResponseModel> buyList;
  private List<ItemBuySettlementListResponseModel> settlementList;


}
