package com.labshigh.aicfo.internal.api.withdraw.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawVerifyEmailRequestModel {

    private long memberUid;
    private String token;
    private String code;

    private long memberWalletWithdrawalUid;
}
