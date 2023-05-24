package com.labshigh.aicfo.internal.api.wallet.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemberWalletWithdrawalHistory {
    private long uid;
    @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
    private LocalDateTime requestAt;
    private BigDecimal requestQuantity;
    private boolean deletedFlag;
    private String requestStatus;
    private String rejectMemo;
}
