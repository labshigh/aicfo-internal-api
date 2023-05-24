package com.labshigh.aicfo.internal.api.withdraw.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class WithdrawResponseModel {
    private long uid;
    private long memberUid;
    @JsonFormat(pattern = Constants.VIEW_DATE_FORMAT)
    private LocalDateTime updatedAt;
    @JsonFormat(pattern = Constants.VIEW_DATE_FORMAT)
    private LocalDateTime createdAt;

    private String email;
    private String txHash;
    private String fromWallet;
    private String toWallet;
    private BigDecimal requestQuantity;
    private boolean confirmStatus;
    private boolean addConfirmStatus;
    private Long rejectUid;
    private String rejectMemo;
    private String confirmStatusCode;
    private String addConfirmStatusCode;
    private String rejectName;
    @JsonFormat(pattern = Constants.VIEW_DATE_FORMAT)
    private LocalDateTime confirmDate;
    @JsonFormat(pattern = Constants.VIEW_DATE_FORMAT)
    private LocalDateTime addConfirmDate;
    @JsonFormat(pattern = Constants.VIEW_DATE_FORMAT)
    private LocalDateTime rejectDate;
    private String rejectSequence;
    private BigDecimal krwPrice;
    private Long transactionId;
    private boolean internalFlag;
    private String adminName;
    private String memberNickname;
    private String memberEmail;
}
