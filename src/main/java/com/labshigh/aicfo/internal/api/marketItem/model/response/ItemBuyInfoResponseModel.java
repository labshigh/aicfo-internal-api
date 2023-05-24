package com.labshigh.aicfo.internal.api.marketItem.model.response;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemBuyInfoResponseModel {

  private List<ItemBuyListResponseModel> list;
  private Map<Long, String> totalPrice;

}
