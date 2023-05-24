package com.labshigh.aicfo.internal.api.transaction.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionHistoryWalletInsertRequestModel {

  private Double price;
  private Long tokenId;
  private Long coinId;
  private String userId;
  private long transactionKindUid;
  private Long transactionUid;
}
