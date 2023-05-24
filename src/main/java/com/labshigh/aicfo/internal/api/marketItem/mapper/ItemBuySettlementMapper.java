package com.labshigh.aicfo.internal.api.marketItem.mapper;

import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuySettlementDao;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuySettlementListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ItemBuySettlementMapper {

  void insert(ItemBuySettlementDao dao);

  void updateWithdrawalApprovalFlag(ItemBuySettlementDao dao);

  void deleteItemBuySettlementByWithdrawal(ItemBuySettlementDao dao);

  List<ItemBuySettlementDao> list(ItemBuySettlementListRequestModel requestModel);

}
