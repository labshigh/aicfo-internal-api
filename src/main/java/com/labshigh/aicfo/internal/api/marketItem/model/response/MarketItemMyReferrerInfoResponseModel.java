package com.labshigh.aicfo.internal.api.marketItem.model.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MarketItemMyReferrerInfoResponseModel {

  private boolean pointAbleFlag;
  private List<MarketItemMyReferrerResponseModel> list;
}
