package com.labshigh.aicfo.internal.api.withdraw.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawDao {
    private long uid;
    private long memberUid;
    private String toUserId;
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
    private Long memberWalletWithdrawalUid;
    private BigDecimal krwPrice;
    private String userId;
    private Long transactionId;
    private boolean internalFlag;
    private String adminName;
    private String memberNickname;
    private String memberEmail;

}
