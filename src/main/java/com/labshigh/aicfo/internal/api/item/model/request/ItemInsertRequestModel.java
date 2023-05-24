package com.labshigh.aicfo.internal.api.item.model.request;


import com.labshigh.aicfo.internal.api.common.Constants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class ItemInsertRequestModel {

  private boolean vipFlag;
  private String round;
  private String closeRound;
  private String interest;
  @DateTimeFormat(pattern = Constants.VIEW_DATE_FORMAT)
  private LocalDateTime startAt;
  @DateTimeFormat(pattern = Constants.VIEW_DATE_FORMAT)
  private LocalDateTime endAt;
  @DateTimeFormat(pattern = Constants.VIEW_DATE_FORMAT)
  private LocalDateTime requestEndAt;
  @DateTimeFormat(pattern = Constants.VIEW_DATE_FORMAT)
  private LocalDateTime withdrawalRequestEndAt;
  private BigDecimal minPrice;
  private long autoItemUid;
  private int itemKind;
}
