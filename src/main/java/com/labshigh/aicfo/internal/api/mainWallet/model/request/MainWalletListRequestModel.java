package com.labshigh.aicfo.internal.api.mainWallet.model.request;

import com.labshigh.aicfo.core.models.RequestPageModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainWalletListRequestModel  extends RequestPageModel {
    private String startAt;
    private String endAt;
}
