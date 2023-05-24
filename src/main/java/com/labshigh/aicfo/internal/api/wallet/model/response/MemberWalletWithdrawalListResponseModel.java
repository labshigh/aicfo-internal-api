package com.labshigh.aicfo.internal.api.wallet.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
public class MemberWalletWithdrawalListResponseModel {

  private List<MemberWalletWithdrawalResponseModel> list;



}
