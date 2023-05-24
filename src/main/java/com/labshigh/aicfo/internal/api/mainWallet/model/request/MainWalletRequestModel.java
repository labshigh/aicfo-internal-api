package com.labshigh.aicfo.internal.api.mainWallet.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MainWalletRequestModel {
    private String walletAddress;
    private BigDecimal amount;
    private long adminUid;

}
