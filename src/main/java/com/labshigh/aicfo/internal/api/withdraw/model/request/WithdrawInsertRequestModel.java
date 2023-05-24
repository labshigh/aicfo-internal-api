package com.labshigh.aicfo.internal.api.withdraw.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawInsertRequestModel {
    private long uid;
    private long memberWalletWithdrawalUid;
    private long memberUid;
    private String fromWallet;
    private String toWallet;
    private BigDecimal requestQuantity;
    private String userId;
    private String walletAddress;
    private long adminUid;
    private long walletId;
    private boolean internalFlag;
}
