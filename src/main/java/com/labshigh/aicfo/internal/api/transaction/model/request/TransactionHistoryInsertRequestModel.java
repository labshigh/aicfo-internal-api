package com.labshigh.aicfo.internal.api.transaction.model.request;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionHistoryInsertRequestModel {

  private BigDecimal price;
  private String unit;
  private long memberUid;
  private long transactionKindUid;
  private String transactionKindName;
  private Long transactionUid;
  private String txHash;
}
