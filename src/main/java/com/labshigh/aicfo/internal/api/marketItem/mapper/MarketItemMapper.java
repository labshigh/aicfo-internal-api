package com.labshigh.aicfo.internal.api.marketItem.mapper;

import com.labshigh.aicfo.internal.api.marketItem.dao.ItemDetailDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.MarketItemAdminVipTokenStakingDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.MarketItemByReferrerDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.MarketItemDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.MarketItemMyReferrerDao;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemAdminVipTokenStakingRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemByReferrerRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemMyReferrerRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MarketItemMapper {

  void insert(MarketItemDao dao);

  void updateUseItem(MarketItemDao dao);

  List<ItemDetailDao> list(MarketItemListRequestModel requestModel);

  List<ItemDetailDao> listRequestItem(MarketItemListRequestModel requestModel);

  int countRequestItem(MarketItemListRequestModel requestModel);

  List<MarketItemByReferrerDao> listRequestItemByReferrer(
      MarketItemByReferrerRequestModel requestModel);

  int countRequestItemByReferrer(MarketItemByReferrerRequestModel requestModel);

  List<MarketItemMyReferrerDao> listReferrer(MarketItemMyReferrerRequestModel requestModel);

  boolean selectReferrerAbleFlag(MarketItemMyReferrerRequestModel requestModel);

  int countAdminVipTokenStakingList(MarketItemAdminVipTokenStakingRequestModel requestModel);

  List<MarketItemAdminVipTokenStakingDao> listAdminVipTokenStaking(
      MarketItemAdminVipTokenStakingRequestModel requestModel);

}
