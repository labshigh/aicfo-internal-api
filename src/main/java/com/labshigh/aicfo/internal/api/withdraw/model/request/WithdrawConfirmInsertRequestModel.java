package com.labshigh.aicfo.internal.api.withdraw.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawConfirmInsertRequestModel {
    private long uid;
    private long withdrawRequestUid;
    private boolean confirmStatus;
    private Long rejectUid;
    private String rejectMemo;
    private BigDecimal requestQuantity;
    private Long transactionId;
    private long adminUid;
}
