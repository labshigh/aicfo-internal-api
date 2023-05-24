package com.labshigh.aicfo.internal.api.wallet.model.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberWalletAdminTotalBalanceResponseModel {

  private BigDecimal totalBalance;
  private String totalBalanceStr;
}
