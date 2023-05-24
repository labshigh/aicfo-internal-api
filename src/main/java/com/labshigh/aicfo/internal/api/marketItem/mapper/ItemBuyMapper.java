package com.labshigh.aicfo.internal.api.marketItem.mapper;

import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyAdminBuySettlementListDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyAdminBuyWithdrawalRequestDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyAdminDetailListByMemberDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyAdminDetailListDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyAdminListDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyAdminStatisticsDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyCurPriceInfoDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyDetailDao;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminBuySettlementListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminDetailListByMemberRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminDetailListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminStatisticsRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminWithdrawalRequestRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyListByUidRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ItemBuyMapper {

  void insert(ItemBuyDao dao);

  List<ItemBuyAdminListDao> adminBuyList(ItemBuyAdminListRequestModel requestModel);

  List<ItemBuyAdminDetailListDao> adminBuyDetailList(
      ItemBuyAdminDetailListRequestModel requestModel);

  int countAdminBuyDetail(ItemBuyAdminDetailListRequestModel requestModel);

  int countAdminBuyDetailListByMember(
      ItemBuyAdminDetailListByMemberRequestModel requestModel);

  List<ItemBuyAdminDetailListByMemberDao> adminBuyDetailListByMember(
      ItemBuyAdminDetailListByMemberRequestModel requestModel);

  int countAdminBuyWithdrawalRequestList(ItemBuyAdminWithdrawalRequestRequestModel requestModel);

  List<ItemBuyAdminBuyWithdrawalRequestDao> adminBuyWithdrawalRequestList(
      ItemBuyAdminWithdrawalRequestRequestModel requestModel);

  List<ItemBuyDetailDao> list(ItemBuyListRequestModel requestModel);

  List<ItemBuyDetailDao> list(ItemBuyDetailDao requestModel);

  int count(@Param(value = "request") ItemBuyListRequestModel requestModel);

  ItemBuyCurPriceInfoDao selectItemBuyCurPriceInfo(ItemBuyDetailDao requestModel);

  ItemBuyCurPriceInfoDao selectItemBuyCurWaitPrice(ItemBuyDetailDao requestModel);


  List<ItemBuyAdminBuySettlementListDao> adminBuySettlementList(
      ItemBuyAdminBuySettlementListRequestModel requestModel);

  int countAdminBuySettlement(
      ItemBuyAdminBuySettlementListRequestModel requestModel);

  List<ItemBuyAdminStatisticsDao> selectAdminBuyStatistics(
      ItemBuyAdminStatisticsRequestModel requestModel);

  int countAdminBuyStatistics(ItemBuyAdminStatisticsRequestModel requestModel);

  List<ItemBuyDetailDao> listByUid(ItemBuyListByUidRequestModel requestModel);

  int countByMember(@Param(value = "request") ItemBuyListRequestModel requestModel);

  List<ItemBuyDetailDao> listByMember(
      @Param(value = "request") ItemBuyListRequestModel requestModel);


}
