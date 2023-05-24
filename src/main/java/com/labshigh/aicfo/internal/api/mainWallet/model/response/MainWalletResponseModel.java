package com.labshigh.aicfo.internal.api.mainWallet.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MainWalletResponseModel {
    private long uid;
    private String walletAddress;
    private String toUserId;
    private int coinId;
    private int tokenId;
    private String memo;
    private BigDecimal amount;
    private BigDecimal gasAmount;
    private BigDecimal totalAmount;
    private long memberUid;
    private boolean manualFlag;
    @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
    @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
    private LocalDateTime updateAt;
    private String txHash;
    private long memberWalletWithdrawalUid;
    private String withdrawalStatus;
    private String adminName;
    @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
    @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
    private LocalDateTime createdAt;
}
