package com.labshigh.aicfo.internal.api.item.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
public class ItemInsertRequestModel_bak {

  private boolean vipFlag;
  private String imageUri;
  private String name;
  private BigDecimal price;
  private BigDecimal wonPrice;
  private long quantity;
  private long currentQuantity;
  private String tokenUri;
  private int itemKind;
  private boolean midSaleFlag;
  private int sort;
  private String interest;
  private String description;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDateTime startAt;
  private long durationId;
  private long adminWalletUid;
}
