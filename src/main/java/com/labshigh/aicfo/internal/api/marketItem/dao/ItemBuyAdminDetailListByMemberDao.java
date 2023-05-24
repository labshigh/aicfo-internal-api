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
public class ItemBuyAdminDetailListByMemberDao {

  private long uid;
  private long itemUid;
  private String email;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  private BigDecimal price;
  private String priceStr;
  private BigDecimal requestPrice;

  private BigDecimal depositPrice;

  private BigDecimal withdrawalPrice;
  

  private BigDecimal interestPrice;
  private String round;
  private String prevRound;
  private String autoRound;
  private boolean autoProgressFlag;
}
