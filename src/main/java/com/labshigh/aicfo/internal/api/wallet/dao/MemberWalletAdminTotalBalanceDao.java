package com.labshigh.aicfo.internal.api.wallet.dao;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberWalletAdminTotalBalanceDao {

  private BigDecimal totalBalance;
}