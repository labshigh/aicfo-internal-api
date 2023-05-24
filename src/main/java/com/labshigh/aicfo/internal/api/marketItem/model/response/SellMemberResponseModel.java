package com.labshigh.aicfo.internal.api.marketItem.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SellMemberResponseModel {

  private long memberUid;
  private String walletAddress;
}
