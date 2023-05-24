package com.labshigh.aicfo.internal.api.withdraw.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawRequestModel {
    private long uid;
    private boolean confirmStatus;
    private boolean addConfirmStatus;
    private Long rejectUid;
    private String rejectMemo;
}
