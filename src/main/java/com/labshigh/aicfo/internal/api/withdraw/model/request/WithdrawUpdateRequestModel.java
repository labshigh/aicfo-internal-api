package com.labshigh.aicfo.internal.api.withdraw.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawUpdateRequestModel {
    private long uid;

    private String txHash;
}
