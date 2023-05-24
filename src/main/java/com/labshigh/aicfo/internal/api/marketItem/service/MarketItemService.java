package com.labshigh.aicfo.internal.api.marketItem.service;

import com.labshigh.aicfo.core.models.ResponseListModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.common.utils.TelegramUtils;
import com.labshigh.aicfo.internal.api.item.dao.ItemDao;
import com.labshigh.aicfo.internal.api.item.mapper.ItemMapper;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyAdminBuySettlementListDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyAdminBuyWithdrawalRequestDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyAdminDetailListByMemberDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyAdminDetailListDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyAdminListDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyAdminStatisticsDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyCurPriceInfoDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuyDetailDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemBuySettlementDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.ItemDetailDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.MarketItemAdminVipTokenStakingDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.MarketItemByReferrerDao;
import com.labshigh.aicfo.internal.api.marketItem.dao.MarketItemMyReferrerDao;
import com.labshigh.aicfo.internal.api.marketItem.mapper.ItemBuyMapper;
import com.labshigh.aicfo.internal.api.marketItem.mapper.ItemBuySettlementMapper;
import com.labshigh.aicfo.internal.api.marketItem.mapper.MarketItemMapper;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminBuySettlementListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminDetailListByMemberRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminDetailListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminStatisticsRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminWithdrawalRequestRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyCurPriceInfoRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyInsertRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyInsertRequestModel.PriceUnit;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuySettlementDeleteWithdrawalRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuySettlementDeleteWithdrawalRequestModel.RequestType;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuySettlementInsertRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuySettlementInsertRequestModel.SettlementType;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuySettlementListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuySettlementUpdateWithdrawalApprovalFlagRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemAdminVipTokenStakingRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemByReferrerRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemMyReferrerRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemUpdateRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.ItemBuyAdminBuySettlementListResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.ItemBuyAdminDetailListByMemberResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.ItemBuyAdminDetailListResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.ItemBuyAdminListResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.ItemBuyAdminStatisticsResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.ItemBuyAdminWithdrawalRequestResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.ItemBuyCurPriceInfoResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.ItemBuyListResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.ItemBuySettlementListResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.MarketItemAdminVipTokenStakingResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.MarketItemByReferrerResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.MarketItemListResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.MarketItemMyReferrerInfoResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.MarketItemMyReferrerResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.ProfitModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.ProfitResponseModel;
import com.labshigh.aicfo.internal.api.member.dao.MemberDao;
import com.labshigh.aicfo.internal.api.member.mapper.MemberMapper;
import com.labshigh.aicfo.internal.api.transaction.dao.TransactionHistoryDao;
import com.labshigh.aicfo.internal.api.transaction.mapper.TransactionHistoryMapper;
import com.labshigh.aicfo.internal.api.wallet.model.request.WalletMEthTransactionPutRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.request.WalletTransactionPutRequestModel;
import com.labshigh.aicfo.internal.api.wallet.service.MemberWalletService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.naming.AuthenticationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MarketItemService {

  @Value("${ncloud.object-storage.end-point}")
  private String s3EndPoint;
  @Value("${ncloud.object-storage.bucket}")
  private String s3NftBucket;

  @Autowired
  private MemberWalletService memberWalletService;
  @Autowired
  private MarketItemMapper marketItemMapper;
  @Autowired
  private MemberMapper memberMapper;
  @Autowired
  private ItemMapper itemMapper;
  @Autowired
  private ItemBuyMapper itemBuyMapper;
  @Autowired
  private TransactionHistoryMapper transactionHistoryMapper;
  @Autowired
  private ItemBuySettlementMapper itemBuySettlementMapper;
  private final TelegramUtils telegramUtils;


  @Transactional
  public MarketItemListResponseModel insertItemBuy(ItemBuyInsertRequestModel requestModel)
      throws AuthenticationException {
    MarketItemListRequestModel marketItemListRequestModel = new MarketItemListRequestModel();
    marketItemListRequestModel.setItemUid(requestModel.getItemUid());

    MemberDao memberDao = memberMapper.get(
        MemberDao.builder().uid(requestModel.getMemberUid()).build());

    if (memberDao == null || !memberDao.isEmailVerifiedFlag()) {
      throw new ServiceException(Constants.MSG_NOT_VERIFIED_EMAIL);
    }

    List<ItemDetailDao> itemDaoList = marketItemMapper.list(marketItemListRequestModel);

    if (itemDaoList.isEmpty()) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }
    ItemDetailDao itemDetailDao = itemDaoList.get(0);

    LocalDateTime now = LocalDateTime.now();

    if (itemDetailDao.getRequestEndAt().isBefore(now)) {
      throw new ServiceException(Constants.MSG_MARKET_ITEM_CLOSE_ERROR);
    }

    if (requestModel.getPrice().compareTo(itemDetailDao.getMinPrice()) < 0) {
      throw new ServiceException(Constants.MSG_MARKET_ITEM_MIN_PRICE_ERROR);
    }

//    long days = ChronoUnit.DAYS.between(now, itemDetailDao.getStartAt());
//
//    if (days < 2) {
//      throw new ServiceException(Constants.MSG_MARKET_ITEM_CLOSE_ERROR);
//    }

//    if (marketItemDetailDao.getCurrentQuantity() - requestModel.getQuantity() < 0) {
//      throw new ServiceException(Constants.MSG_ITEM_BUY_CURRENT_QUANTITY_ERROR);
//    }
//
//      ItemDao itemDao = ItemDao.builder()
//          .uid(marketItemDetailDao.getItemUid())
////          .quantity(requestModel.getQuantity())
////          .currentQuantity(requestModel.getQuantity())
//          .itemKind(requestModel.getItemKind())
//          //   .index(index)
//          .build();
//
//         itemMapper.insertBuyItem(itemDao);

    ItemBuyDao itemBuyDao = ItemBuyDao.builder()
        .price(requestModel.getPrice())
        .memberUid(requestModel.getMemberUid())
        .marketItemUid(0)
        .priceUnit(requestModel.getPriceUnit().name())
        .itemUid(itemDetailDao.getUid())
        .userId(requestModel.getUserId())
        .build();
    itemBuyMapper.insert(itemBuyDao);

    //히스토리 내역 insert
    TransactionHistoryDao transactionHistoryDao = TransactionHistoryDao.builder()
        .price(itemBuyDao.getPrice())
        .unit(requestModel.getPriceUnit().name())
        .memberUid(itemBuyDao.getMemberUid())
        .transactionKindUid(13L)
        .transactionUid(itemBuyDao.getUid())
        .userId(requestModel.getUserId())
        .build();

    transactionHistoryMapper.insert(transactionHistoryDao);

    // 스테이킹 구매 신청 (금액 단위가 ETH인 경우에만 적용)
    if (requestModel.getPriceUnit().equals(PriceUnit.ETH)) {
      walletTransaction(requestModel.getUserId(), requestModel.getMemberUid(),
          requestModel.getPrice(), "13");
//
//      WalletTransactionPutRequestModel walletTransactionPutRequestModel =
//          WalletTransactionPutRequestModel.builder()
//              .userId(Long.toString(requestModel.getMemberUid()))
//              .tokenId(2L)
//              .coinId(2L)
//              .type("13") // Staking 신청
//              .amount(requestModel.getPrice().doubleValue() * -1)
//              .memo("Staking 신청")
//              .event(false)
//              .memberUid(requestModel.getMemberUid())
//              .build();
//      memberWalletService.postTransactionsPut(walletTransactionPutRequestModel);
    } else {
      //mETH 차감 로직 적용 필요

      WalletMEthTransactionPutRequestModel walletMEthTransactionPutRequestModel = WalletMEthTransactionPutRequestModel.builder()
          .methUseAmount(requestModel.getPrice().multiply(BigDecimal.valueOf(-1)))
          .methType(21L)
          .memo("스테이킹 신청")
          .memberUid(requestModel.getMemberUid())
          .walletId(requestModel.getWalletId())
          .itemBuyUid(itemBuyDao.getUid())
          .itemUid(itemBuyDao.getItemUid())
          .build();

      memberWalletService.postMEthTransactionsPut(walletMEthTransactionPutRequestModel);

    }
//    MarketItemDao marketItemDao = MarketItemDao.builder()
//        .currentQuantity(requestModel.getCurrentQuantity())
//        .uid(requestModel.getMarketItemUid())
//        .build();
//    marketItemMapper.updateCurrentQuantity(marketItemDao);

    //return convertItemBuyResponseModel(itemBuyDao);

    return covertMarketItemListResponseModel(itemDetailDao);
  }

  public List<MarketItemListResponseModel> list(MarketItemListRequestModel requestModel) {
    return marketItemMapper.list(requestModel).stream()
        .map(v -> covertMarketItemListResponseModel(v, requestModel)).collect(Collectors.toList());
  }

  public List<MarketItemListResponseModel> listRequestItem(
      MarketItemListRequestModel requestModel) {
    return marketItemMapper.listRequestItem(requestModel).stream()
        .map(v -> covertMarketItemListResponseModel(v, requestModel)).collect(Collectors.toList());
  }

  public ResponseListModel listRequestItemResponseList(
      MarketItemListRequestModel requestModel) {

    ResponseListModel result = new ResponseListModel();

    int totalCount = marketItemMapper.countRequestItem(requestModel);
    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;
    }

    List<MarketItemListResponseModel> list = marketItemMapper.listRequestItem(requestModel).stream()
        .map(v -> covertMarketItemListResponseModel(v, requestModel)).collect(Collectors.toList());

    result.setList(list);

    return result;
  }

  public ResponseListModel listAdminVipTokenStaking(
      MarketItemAdminVipTokenStakingRequestModel requestModel) {

    ResponseListModel result = new ResponseListModel();

    int totalCount = marketItemMapper.countAdminVipTokenStakingList(requestModel);
    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;
    }

    List<MarketItemAdminVipTokenStakingResponseModel> list = marketItemMapper.listAdminVipTokenStaking(
            requestModel).stream()
        .map(this::convertMarketItemAdminVipTokenStakingResponseModel).collect(Collectors.toList());

    result.setList(list);

    return result;
  }

  public List<MarketItemMyReferrerResponseModel> listRequestItemMyReferrer(
      MarketItemMyReferrerRequestModel requestModel) {
    return marketItemMapper.listReferrer(requestModel).stream()
        .map(this::convertMarketItemMyReferrerResponseModel).collect(Collectors.toList());
  }

  public MarketItemMyReferrerInfoResponseModel listStakingItemMyReferrer(
      MarketItemMyReferrerRequestModel requestModel) {

    List<MarketItemMyReferrerResponseModel> list = marketItemMapper.listReferrer(requestModel)
        .stream()
        .map(this::convertMarketItemMyReferrerResponseModel).collect(Collectors.toList());
    boolean pointAbleFlag = marketItemMapper.selectReferrerAbleFlag(requestModel);
    return MarketItemMyReferrerInfoResponseModel.builder().list(list).pointAbleFlag(pointAbleFlag)
        .build();
  }

  public List<MarketItemByReferrerResponseModel> listRequestItemByReferrer(
      MarketItemByReferrerRequestModel requestModel) {
    return marketItemMapper.listRequestItemByReferrer(requestModel).stream()
        .map(this::covertMarketItemByReferrerResponseModel).collect(Collectors.toList());
  }

  public ResponseListModel listRequestItemByReferrerResponseList(
      MarketItemByReferrerRequestModel requestModel) {

    ResponseListModel result = new ResponseListModel();

    int totalCount = marketItemMapper.countRequestItemByReferrer(requestModel);
    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;
    }

    List<MarketItemByReferrerResponseModel> list = marketItemMapper.listRequestItemByReferrer(
            requestModel).stream()
        .map(this::covertMarketItemByReferrerResponseModel).collect(Collectors.toList());

    result.setList(list);

    return result;
  }


  public List<ItemBuyAdminListResponseModel> listAdminBuyItem(
      ItemBuyAdminListRequestModel requestModel) {

    return itemBuyMapper.adminBuyList(requestModel).stream()
        .map(this::convertItemBuyAdminListResponseModel)
        .collect(Collectors.toList());
  }

  public ResponseListModel detailListAdminBuyItem(ItemBuyAdminDetailListRequestModel requestModel) {
    ResponseListModel result = new ResponseListModel();

    int totalCount = itemBuyMapper.countAdminBuyDetail(requestModel);

    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;
    }

    List<ItemBuyAdminDetailListResponseModel> list = itemBuyMapper.adminBuyDetailList(requestModel)
        .stream().map(this::convertItemBuyAdminDetailListResponseModel)
        .collect(Collectors.toList());

    result.setList(list);

    return result;
  }

  public ResponseListModel listAdminBuyItemStatistics(
      ItemBuyAdminStatisticsRequestModel requestModel) {
    ResponseListModel result = new ResponseListModel();

    int totalCount = itemBuyMapper.countAdminBuyStatistics(requestModel);

    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;
    }

    List<ItemBuyAdminStatisticsResponseModel> list = itemBuyMapper.selectAdminBuyStatistics(
            requestModel)
        .stream().map(this::convertItemBuyAdminStatisticsResponseModel)
        .collect(Collectors.toList());

    result.setList(list);

    return result;
  }

  public ResponseListModel withdrawalRequestListAdminBuyItem(
      ItemBuyAdminWithdrawalRequestRequestModel requestModel) {
    ResponseListModel result = new ResponseListModel();

    int totalCount = itemBuyMapper.countAdminBuyWithdrawalRequestList(requestModel);

    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;
    }

    List<ItemBuyAdminWithdrawalRequestResponseModel> list = itemBuyMapper.adminBuyWithdrawalRequestList(
            requestModel)
        .stream().map(this::convertItemBuyWithdrawalRequestResponseModel)
        .collect(Collectors.toList());

    result.setList(list);

    return result;
  }

  public List<ItemBuyAdminDetailListByMemberResponseModel> detailListAdminBuyItemByMember(
      ItemBuyAdminDetailListByMemberRequestModel requestModel) {
    List<ItemBuyAdminDetailListByMemberDao> daoList = itemBuyMapper.adminBuyDetailListByMember(
        requestModel);

    return daoList.stream().map(this::convertItemBuyAdminDetailListByMemberResponseModel)
        .collect(Collectors.toList());

  }

  public ResponseListModel autoProcListAdminBuyItem(
      ItemBuyAdminDetailListByMemberRequestModel requestModel) {

    ResponseListModel result = new ResponseListModel();

    int totalCount = itemBuyMapper.countAdminBuyDetailListByMember(requestModel);

    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;
    }
    result.setList(detailListAdminBuyItemByMember(requestModel));

    return result;
  }

  public ResponseListModel adminBuySettlementList(
      ItemBuyAdminBuySettlementListRequestModel requestModel) {

    ResponseListModel result = new ResponseListModel();

    int totalCount = itemBuyMapper.countAdminBuySettlement(requestModel);

    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;
    }
    result.setList(itemBuyMapper.adminBuySettlementList(requestModel).stream()
        .map(this::convertItemBuyAdminBuySettlementListRequestModel).collect(
            Collectors.toList()));

    return result;
  }

  @Transactional
  public void updateWithdrawalApprovalFlag(
      ItemBuySettlementUpdateWithdrawalApprovalFlagRequestModel requestModel) {
    itemBuySettlementMapper.updateWithdrawalApprovalFlag(ItemBuySettlementDao.builder()
        .uid(requestModel.getUid())
        .build());
  }

  @Transactional
  public void deleteItemBuySettlementByWithdrawal(
      ItemBuySettlementDeleteWithdrawalRequestModel requestModel) {

    ItemBuySettlementListRequestModel listRequestModel = new ItemBuySettlementListRequestModel();
    listRequestModel.setUid(requestModel.getUid());
    listRequestModel.setUserId(requestModel.getUserId());
    List<ItemBuySettlementDao> settlementDaoList = itemBuySettlementMapper.list(listRequestModel);

    if (settlementDaoList.isEmpty()) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }
    ItemBuySettlementDao settlementDao = settlementDaoList.get(0);
    boolean validate;
    String exceptionMsg = requestModel.getRequestType().equals(RequestType.MEMBER)
        ? Constants.MSG_MARKET_ITEM_MEMBER_WITHDRAWAL_DELETE_ERROR
        : Constants.MSG_MARKET_ITEM_ADMIN_WITHDRAWAL_DELETE_ERROR;
    switch (requestModel.getRequestType()) {
      case MEMBER:
        validate = settlementDao.isWithdrawalApprovalFlag();
        break;
      case ADMIN:
        validate = !settlementDao.isWithdrawalApprovalFlag();
        break;
      default:
        validate = true;
        break;
    }

    if (validate) {
      throw new ServiceException(exceptionMsg);
    }

    itemBuySettlementMapper.deleteItemBuySettlementByWithdrawal(settlementDao);


  }


  @Transactional
  public void updateItem(MarketItemUpdateRequestModel requestModel) {
    MarketItemListRequestModel marketItemListRequestModel = new MarketItemListRequestModel();
    marketItemListRequestModel.setItemUid(requestModel.getUid());
    List<ItemDetailDao> daoList = marketItemMapper.list(marketItemListRequestModel);

    if (daoList.isEmpty()) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    LocalDateTime now = LocalDateTime.now();

    if (daoList.get(0).getStartAt().isBefore(now)) {
      throw new ServiceException(Constants.MSG_MARKET_ITEM_UPDATE_START_AT_ERROR);
    }

    itemMapper.updateItem(ItemDao.builder()
        .uid(requestModel.getUid())
        .startAt(requestModel.getStartAt())
        .requestEndAt(requestModel.getRequestEndAt())
        .endAt(requestModel.getEndAt())
        .interest(requestModel.getInterest())
        .minPrice(requestModel.getMinPrice())
        .round(requestModel.getRound())
        .itemKind(requestModel.getItemKind())
        .autoItemUid(requestModel.getAutoItemUid())
        .build());

  }

  @Transactional
  public ItemBuySettlementListResponseModel insertItemBuySettlement(
      ItemBuySettlementInsertRequestModel requestModel) {
    ItemBuyListRequestModel itemBuyListRequestModel = new ItemBuyListRequestModel();
    itemBuyListRequestModel.setItemUid(requestModel.getUid());
    itemBuyListRequestModel.setMemberUid(requestModel.getMemberUid());
    itemBuyListRequestModel.setUserId(requestModel.getUserId());
    itemBuyListRequestModel.setProcessStatus("4");

    List<ItemBuyDetailDao> itemBuyDetailList = itemBuyMapper.list(itemBuyListRequestModel);

    if (itemBuyDetailList.isEmpty()) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    ItemBuyDetailDao itemBuyDetailDao = itemBuyDetailList.get(0);

    LocalDateTime now = LocalDateTime.now();

    if (now.isAfter(itemBuyDetailDao.getWithdrawalRequestEndAt()) && now.isBefore(
        itemBuyDetailDao.getEndAt())) {
      throw new ServiceException(Constants.MSG_MARKET_ITEM_CLOSE_WITHDRAWAL_ERROR);
    }

    //출금 요청
    if (requestModel.getSettlementType().equals(SettlementType.WITHDRAWAL)) {
//      BigDecimal withdrawalTotalPrice = requestModel.getPrice()
//          .add(itemBuyDetailDao.getWithdrawalTotalPrice());
//      if (withdrawalTotalPrice.compareTo(
//          itemBuyDetailDao.getPrice().add(itemBuyDetailDao.getInterestPrice())) > 0) {
//        throw new ServiceException(Constants.MSG_MARKET_ITEM_WITHDRAWAL_PRICE_OVER);
//      }

      BigDecimal totalPrice;

      BigDecimal totalInterestPrice;

      totalPrice = itemBuyDetailList.stream().map(ItemBuyDetailDao::getPrice)
          .reduce(BigDecimal.ZERO, BigDecimal::add);
      totalInterestPrice = itemBuyDetailList.stream().map(ItemBuyDetailDao::getInterestPrice)
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      BigDecimal totalBalance = totalPrice.add(totalInterestPrice);

      ItemBuySettlementListRequestModel itemBuySettlementListRequestModel = new ItemBuySettlementListRequestModel();
      itemBuySettlementListRequestModel.setMemberUid(requestModel.getMemberUid());
      itemBuySettlementListRequestModel.setItemUid(requestModel.getUid());
      itemBuySettlementListRequestModel.setUserId(requestModel.getUserId());

      List<ItemBuySettlementListResponseModel> settlementList = this.listItemBuySettlement(
          itemBuySettlementListRequestModel);

      long countWithdrawal = settlementList.stream()
          .filter(v -> v.getType().equals(SettlementType.WITHDRAWAL.name())).count();

      if (countWithdrawal > 0) {
        throw new ServiceException(Constants.MSG_MARKET_ITEM_ALREADY_WITHDRAWAL_REQUEST);
      }
//
//      //출금 총합 계산
//      BigDecimal totWithdrawalPrice = settlementList.stream()
//          .filter(v -> v.getType().equals(SettlementType.WITHDRAWAL.name()))
//          .map(ItemBuySettlementListResponseModel::getPrice)
//          .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//      if (totWithdrawalPrice.add(requestModel.getPrice()).compareTo(totalBalance) > 0) {
//        throw new ServiceException(Constants.MSG_MARKET_ITEM_WITHDRAWAL_PRICE_OVER);
//      }
      if (requestModel.getPrice().compareTo(totalBalance) > 0) {
        throw new ServiceException(Constants.MSG_MARKET_ITEM_WITHDRAWAL_PRICE_OVER);
      }

      try {
        telegramUtils.postSendOnAdminTracking("Application for new financial settlement : " + requestModel.getUserId());
      } catch (Exception e) {
        throw new ServiceException(e.getMessage());
      }


    } else { // 입금 요청(지갑에서 입금 금액 차감)
      try {
        walletTransaction(requestModel.getUserId(), requestModel.getMemberUid(),
            requestModel.getPrice(), "17");

      } catch (Exception e) {
        throw new ServiceException(e.getMessage());
      }
    }

    ItemBuySettlementDao itemBuySettlementDao = ItemBuySettlementDao.builder()
        .itemUid(itemBuyDetailDao.getItemUid())
        .memberUid(requestModel.getMemberUid())
        .type(requestModel.getSettlementType().name())
        .price(requestModel.getPrice())
        .itemBuyUid(requestModel.getItemBuyUid())
        .userId(requestModel.getUserId())
        .build();

    itemBuySettlementMapper.insert(itemBuySettlementDao);

    if (requestModel.getSettlementType().equals(SettlementType.DEPOSIT)) {
      TransactionHistoryDao transactionHistoryDao = TransactionHistoryDao.builder()
          .price(requestModel.getPrice())
          .unit("ETH")
          .memberUid(requestModel.getMemberUid())
          .transactionKindUid(17L)
          .transactionUid(itemBuySettlementDao.getUid())
          .userId(requestModel.getUserId())
          .build();

      transactionHistoryMapper.insert(transactionHistoryDao);
    }

    return convertItemBuySettlementListResponseModel(itemBuySettlementDao);

  }


  public List<ItemBuyListResponseModel> listItemBuy(ItemBuyListRequestModel requestModel) {

    List<ItemBuyDetailDao> itemBuyDetailDaoList = itemBuyMapper.list(requestModel);

    return itemBuyDetailDaoList.stream()
        .map(this::convertItemBuyListResponseModel).collect(Collectors.toList());
  }

  public Map<Long, String> listItemBuyGroupByItemUid(
      List<ItemBuyListResponseModel> list) {

    Map<Long, List<ItemBuyListResponseModel>> group = list.stream()
        .collect(Collectors.groupingBy(ItemBuyListResponseModel::getItemUid));
    Map<Long, String> result = new HashMap<>();
    for (Long itemUid : group.keySet()) {
      BigDecimal totalPrice = group.get(itemUid).stream().map(ItemBuyListResponseModel::getPrice)
          .reduce(BigDecimal.ZERO, BigDecimal::add);
      result.put(itemUid,
          totalPrice.setScale(3, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
    }
    return result;

  }

  public ProfitResponseModel profitItemBuy(ItemBuyListRequestModel requestModel) {
    ProfitModel responseModel = new ProfitModel();
    if (requestModel.getToDay() != null) {
      requestModel.setProcessStatus("2");
    }

    List<ItemBuyDetailDao> list = itemBuyMapper.list(requestModel);

    if (!list.isEmpty()) {
      for (ItemBuyDetailDao dao : list) {
        responseModel.setPrice(responseModel.getPrice().add(dao.getPrice())); // 투자 ETH

        responseModel.setInterestPrice(
            responseModel.getInterestPrice().add(dao.getInterestPrice())); // 수익 ETH

        long days = ChronoUnit.DAYS.between(dao.getStartAt(), dao.getEndAt());

        BigDecimal interestDayPrice = dao.getInterestPrice()
            .divide(BigDecimal.valueOf(days), 2, RoundingMode.DOWN); // 하루 수익 ETH

        responseModel.setInterestDayPrice(
            responseModel.getInterestDayPrice().add(interestDayPrice));
      }
      if (requestModel.getToDay() == null) {
        long periodDays = ChronoUnit.DAYS.between(requestModel.getStartAt(),
            requestModel.getEndAt());
        responseModel.setInterestDayPrice(
            responseModel.getInterestPrice()
                .divide(BigDecimal.valueOf(periodDays), 2, RoundingMode.DOWN)
        );
      }
      BigDecimal krwEth = list.get(0).getKrwEth();
      responseModel.setKrwPrice(responseModel.getPrice().multiply(krwEth).setScale(2,
          RoundingMode.DOWN));
      responseModel.setKrwInterestPrice(
          responseModel.getInterestPrice().multiply(krwEth).setScale(2,
              RoundingMode.DOWN));
      responseModel.setKrwInterestDayPrice(
          responseModel.getInterestDayPrice().multiply(krwEth).setScale(2,
              RoundingMode.DOWN));

    }

    return ProfitResponseModel.builder()
        .price(responseModel.getPrice().toString())
        .krwPrice(responseModel.getKrwPrice().toString())
        .interestPrice(responseModel.getInterestPrice().setScale(2,
            RoundingMode.DOWN).toString())
        .krwInterestPrice(responseModel.getKrwInterestPrice().toString())
        .interestDayPrice(responseModel.getInterestDayPrice().toString())
        .krwInterestDayPrice(responseModel.getKrwInterestDayPrice().toString())
        .build();
//    NumberFormat format = NumberFormat.getInstance();

//    return ProfitResponseModel.builder()
//        .price(format.format(responseModel.getPrice()))
//        .krwPrice(format.format(responseModel.getKrwPrice()))
//        .interestPrice(format.format(responseModel.getInterestPrice()))
//        .krwInterestPrice(format.format(responseModel.getKrwInterestPrice()))
//        .interestDayPrice(format.format(responseModel.getInterestDayPrice()))
//        .krwInterestPrice(format.format(responseModel.getKrwInterestPrice()))
//        .build();

  }

  public List<ItemBuySettlementListResponseModel> listItemBuySettlement(
      ItemBuySettlementListRequestModel requestModel) {
    List<ItemBuySettlementDao> daoList = itemBuySettlementMapper.list(requestModel);
    return daoList.stream().map(this::convertItemBuySettlementListResponseModel)
        .collect(Collectors.toList());
  }

  public ItemBuyCurPriceInfoResponseModel selectItemBuyCurPriceInfo(
      ItemBuyCurPriceInfoRequestModel requestModel) {
    ItemBuyCurPriceInfoResponseModel result;
    ItemBuyCurPriceInfoDao dao = itemBuyMapper.selectItemBuyCurPriceInfo(
        ItemBuyDetailDao.builder().memberUid(requestModel.getMemberUid())
            .userId(requestModel.getUserId()).build());

    ItemBuyCurPriceInfoDao waitDao = itemBuyMapper.selectItemBuyCurWaitPrice(
        ItemBuyDetailDao.builder().memberUid(requestModel.getMemberUid())
            .userId(requestModel.getUserId()).build());

    result = convertItemBuyCurPriceInfoResponseModel(dao);
    BigDecimal waitPrice = waitDao == null ? BigDecimal.ZERO : waitDao.getWaitPrice();

    result.setWaitTotalPriceStr(
        waitPrice.add(result.getDepositPrice()).setScale(3, RoundingMode.DOWN).toPlainString());

    return result;

  }

  private ItemBuyCurPriceInfoResponseModel convertItemBuyCurPriceInfoResponseModel(
      ItemBuyCurPriceInfoDao dao) {

    if (dao == null) {
      BigDecimal zero = BigDecimal.ZERO.setScale(3, RoundingMode.DOWN);
      return ItemBuyCurPriceInfoResponseModel.builder()
          .totalEthPrice(zero)
          .totalEthPriceStr(zero.toPlainString())
          .totalMethPrice(zero)
          .totalMethPriceStr(zero.toPlainString())
          .totalInterestPrice(zero)
          .totalInterestPriceStr(
              zero.toPlainString())
          .waitTotalPriceStr(
              zero.toPlainString())
          .depositPrice(zero)
          .build();
    }

    return ItemBuyCurPriceInfoResponseModel.builder()
        .totalEthPrice(dao.getTotalEthPrice().setScale(3, RoundingMode.DOWN))
        .totalEthPriceStr(dao.getTotalEthPrice().setScale(3, RoundingMode.DOWN).toPlainString())
        .totalMethPrice(dao.getTotalMethPrice().setScale(3, RoundingMode.DOWN))
        .totalMethPriceStr(dao.getTotalMethPrice().setScale(3, RoundingMode.DOWN).toPlainString())
        .totalInterestPrice(dao.getTotalInterestPrice())
        .depositPrice(dao.getDepositPrice())
        .totalInterestPriceStr(
            dao.getTotalInterestPrice().setScale(3, RoundingMode.DOWN).toPlainString())
        .waitTotalPriceStr(
            dao.getWaitPrice().add(dao.getDepositPrice()).setScale(3, RoundingMode.DOWN)
                .toPlainString())
        .build();
  }

  private void walletTransaction(String userId, Long memberUid, BigDecimal price, String typeCode)
      throws AuthenticationException {
    WalletTransactionPutRequestModel walletTransactionPutRequestModel =
        WalletTransactionPutRequestModel.builder()
            .userId(userId)
            .tokenId(2L)
            .coinId(2L)
            .type(typeCode) // Staking 신청
            .amount(price.doubleValue() * -1)
            .memo("Staking 신청")
            .event(false)
            .memberUid(memberUid)
            .build();
    memberWalletService.postTransactionsPut(walletTransactionPutRequestModel);
  }

  private ItemBuyAdminBuySettlementListResponseModel convertItemBuyAdminBuySettlementListRequestModel(
      ItemBuyAdminBuySettlementListDao dao) {

    BigDecimal totalPrice = dao.getPrice().add(dao.getInterestPrice()).add(dao.getReferrerPrice())
        .setScale(3, RoundingMode.DOWN);

    return ItemBuyAdminBuySettlementListResponseModel.builder()
        .itemUid(dao.getItemUid())
        .email(dao.getEmail())
        .price(dao.getPrice())
        .priceStr(
            dao.getPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros().toPlainString())
        .interestPrice(dao.getInterestPrice())
        .interestPriceStr(dao.getInterestPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
            .toPlainString())
        .referrerPrice(dao.getReferrerPrice())
        .referrerPriceStr(dao.getReferrerPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
            .toPlainString())
        .totalPriceStr(
            totalPrice.setScale(3, RoundingMode.DOWN).stripTrailingZeros().toPlainString())
        .build();

  }

  private ItemBuyAdminListResponseModel convertItemBuyAdminListResponseModel(
      ItemBuyAdminListDao dao) {
    String buyEthPriceStr;
    String buyMethPriceStr;
    String interestPriceStr;
    String totalPriceStr;
    String countRequest;
    String countDeposit;
    String countTotal;
    String countAuto;

    LocalDateTime now = LocalDateTime.now();
//    DecimalFormat format = new DecimalFormat("###,###.###"); //포맷팅

    if (now.isAfter(dao.getRequestEndAt()) || dao.isProcFlag()) {
      buyEthPriceStr =
          dao.getBuyEthTotalPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
              .toPlainString() + " ETH";
      buyMethPriceStr =
          dao.getBuyMethTotalPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
              .toPlainString() + " mETH";
      interestPriceStr =
          dao.getInterestPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
              .toPlainString() + " ETH";
      countRequest = "[" + dao.getCountWithdrawal() + "] / " + dao.getCountRequest();
      countTotal = dao.getCountTotal();

      countDeposit = dao.getCountDeposit();
      countAuto = dao.getCountAutoProc();

      totalPriceStr = dao.getBuyEthTotalPrice().setScale(3, RoundingMode.DOWN)
          .add(dao.getInterestPrice().setScale(3, RoundingMode.DOWN))
          .stripTrailingZeros()
          .toPlainString()
          + " ETH";

      //          format.format(dao.getBuyTotalPrice().setScale(3, RoundingMode.FLOOR)) + " ETH";

    } /*else if (dao.isProcFlag()) {
      buyEthPriceStr = "진행중";
      buyMethPriceStr = "-";
      interestPriceStr = "-";
      totalPriceStr = "-";
      countRequest = "-";
      countDeposit = "-";
      countTotal = "-";
      countAuto = "-";

    } */ else {
      buyEthPriceStr = "대기중";
      buyMethPriceStr = "-";
      interestPriceStr = "-";
      totalPriceStr = "-";
      countRequest = "-";
      countDeposit = "-";
      countTotal = "-";
      countAuto = "-";
    }

    return ItemBuyAdminListResponseModel.builder()
        .uid(dao.getUid())
        .round(dao.getRound())
        .month(dao.getMonth())
        .countBuy(dao.getCountBuy())
        .closeRound(dao.getCloseRound())
        .startAt(dao.getStartAt())
        .endAt(dao.getEndAt())
        .requestEndAt(dao.getRequestEndAt())
        //.buyTotalPrice(dao.getBuyTotalPrice())
        //.buyTotalPriceStr(buyTotalPriceStr)
        .buyEthTotalPrice(dao.getBuyEthTotalPrice())
        .buyEthTotalPriceStr(buyEthPriceStr)
        .buyMethTotalPrice(dao.getBuyMethTotalPrice())
        .buyMethTotalPriceStr(buyMethPriceStr)
        .interestPrice(dao.getInterestPrice())
        .interestPriceStr(interestPriceStr)
        .totalPriceStr(totalPriceStr)
        .countEthBuy(dao.getCountEthBuy())
        .countMethBuy(dao.getCountMethBuy())
        .countAutoProc(countAuto)
        .countRequestWithdrawal(dao.getCountRequestWithdrawal())
        .countRequest(countRequest)
        .countDeposit(countDeposit)
        .countWithdrawal(dao.getCountWithdrawal())
        .countTotal(countTotal)
        .build();
  }

  private ItemBuyAdminStatisticsResponseModel convertItemBuyAdminStatisticsResponseModel(
      ItemBuyAdminStatisticsDao dao) {
    String buyEthPriceStr;
    String buyMethPriceStr;
    String interestPriceStr;
    String totalPriceStr;
    String countRequest;
    String countDeposit;
    String countTotal;
    String countAuto;
    String referrerPriceStr;

    buyEthPriceStr =
        dao.getBuyEthTotalPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
            .toPlainString() + " ETH";
    buyMethPriceStr =
        dao.getBuyMethTotalPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
            .toPlainString() + " mETH";
    interestPriceStr =
        dao.getInterestPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
            .toPlainString() + " ETH";
    countRequest = "[" + dao.getCountWithdrawal() + "] / " + dao.getCountRequest();
    countTotal = dao.getCountTotal();

    countDeposit = dao.getCountDeposit();
    countAuto = dao.getCountAutoProc();

    referrerPriceStr = dao.getReferrerPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
        .toPlainString();

    totalPriceStr = dao.getBuyEthTotalPrice().setScale(3, RoundingMode.DOWN)
        .add(dao.getInterestPrice().setScale(3, RoundingMode.DOWN))
        .add(dao.getReferrerPrice().setScale(3, RoundingMode.DOWN))
        .stripTrailingZeros()
        .toPlainString()
        + " ETH";

    return ItemBuyAdminStatisticsResponseModel.builder()
        .uid(dao.getUid())
        .round(dao.getRound())
        .month(dao.getMonth())
        .countBuy(dao.getCountBuy())
        .closeRound(dao.getCloseRound())
        .startAt(dao.getStartAt())
        .endAt(dao.getEndAt())
        .requestEndAt(dao.getRequestEndAt())
        .buyEthTotalPrice(dao.getBuyEthTotalPrice())
        .buyEthTotalPriceStr(buyEthPriceStr)
        .buyMethTotalPrice(dao.getBuyMethTotalPrice())
        .buyMethTotalPriceStr(buyMethPriceStr)
        .interestPrice(dao.getInterestPrice())
        .interestPriceStr(interestPriceStr)
        .totalPriceStr(totalPriceStr)
        .countEthBuy(dao.getCountEthBuy())
        .countMethBuy(dao.getCountMethBuy())
        .countAutoProc(countAuto)
        .countRequestWithdrawal(dao.getCountRequestWithdrawal())
        .countRequest(countRequest)
        .countDeposit(countDeposit)
        .countWithdrawal(dao.getCountWithdrawal())
        .countTotal(countTotal)
        .referrerPrice(dao.getReferrerPrice())
        .referrerPriceStr(referrerPriceStr)
        .build();
  }


  private ItemBuyAdminDetailListResponseModel convertItemBuyAdminDetailListResponseModel(
      ItemBuyAdminDetailListDao dao) {
//    DecimalFormat format = new DecimalFormat("###,###.###"); //포맷팅
//    String totalPriceStr = format.format(dao.getTotalPrice().setScale(3, RoundingMode.DOWN));

    String totalPriceStr = dao.getTotalPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
        .toPlainString();

    return ItemBuyAdminDetailListResponseModel.builder()
        .itemUid(dao.getItemUid())
        .memberUid(dao.getMemberUid())
        .email(dao.getEmail())
        .totalPrice(dao.getTotalPrice())
        .totalPriceStr(totalPriceStr)
        .requestCount(dao.getRequestCount())
        .autoProgressFlag(dao.isAutoProgressFlag())
        .status(dao.getStatus())
        .build();

  }

  private ItemBuyAdminDetailListByMemberResponseModel convertItemBuyAdminDetailListByMemberResponseModel(
      ItemBuyAdminDetailListByMemberDao dao) {

    BigDecimal totalPrice = dao.getPrice()
        .add(dao.getInterestPrice())
        .add(dao.getDepositPrice())
        .add(dao.getWithdrawalPrice())
        .setScale(3, RoundingMode.DOWN).stripTrailingZeros();

    return ItemBuyAdminDetailListByMemberResponseModel.builder()
        .uid(dao.getUid())
        .itemUid(dao.getItemUid())
        .email(dao.getEmail())
        .createdAt(dao.getCreatedAt())
        .totalPriceStr(totalPrice.toPlainString())
        .price(dao.getPrice())
        .priceStr(
            dao.getPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros().toPlainString())
        .requestPrice(dao.getRequestPrice())
        .requestPriceStr(dao.getRequestPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
            .toPlainString())
        .depositPrice(dao.getDepositPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros())
        .depositPriceStr(dao.getDepositPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
            .toPlainString())
        .withdrawalPrice(
            dao.getWithdrawalPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros())
        .withdrawalPriceStr(
            dao.getWithdrawalPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
                .toPlainString())
        .interestPrice(dao.getInterestPrice())
        .interestPriceStr(
            dao.getInterestPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
                .toPlainString())
        .round(dao.getRound())
        .prevRound(dao.getPrevRound())
        .autoRound(dao.getAutoRound())
        .autoProgressFlag(dao.isAutoProgressFlag())
        .build();
  }

  private ItemBuyAdminWithdrawalRequestResponseModel convertItemBuyWithdrawalRequestResponseModel(
      ItemBuyAdminBuyWithdrawalRequestDao dao) {

    BigDecimal totalPrice = dao.getPrice().setScale(3, RoundingMode.DOWN)
        .add(dao.getInterestPrice().setScale(3, RoundingMode.DOWN)).stripTrailingZeros();

    return ItemBuyAdminWithdrawalRequestResponseModel.builder()
        .itemUid(dao.getItemUid())
        .memberUid(dao.getMemberUid())
        .email(dao.getEmail())
        .withdrawalRequestAt(dao.getWithdrawalRequestAt())
        .withdrawalApprovalAt(dao.getWithdrawalApprovalAt())
        .totalPriceStr(totalPrice.toPlainString())
        .price(dao.getPrice())
        .priceStr(
            dao.getPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros().toPlainString())
        .interestPrice(dao.getInterestPrice())
        .interestPriceStr(
            dao.getInterestPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
                .toPlainString())
        .round(dao.getRound())
        //.autoProgressFlag(dao.isAutoProgressFlag())
        .withdrawalCompletedFlag(dao.isWithdrawalCompletedFlag())
        .withdrawalApprovalFlag(dao.isWithdrawalApprovalFlag())
        .itemBuySettlementUid(dao.getItemBuySettlementUid())
        .withdrawalPrice(dao.getWithdrawalPrice().setScale(3, RoundingMode.DOWN))
        .withdrawalPriceStr(
            dao.getWithdrawalPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
                .toPlainString())
        .autoCount(dao.getAutoCount())
        .build();

  }

  private MarketItemAdminVipTokenStakingResponseModel convertMarketItemAdminVipTokenStakingResponseModel(
      MarketItemAdminVipTokenStakingDao dao) {

    String priceStr = dao.getPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
        .toPlainString();
    String interestPriceStr = dao.getInterestPrice().setScale(3, RoundingMode.DOWN)
        .stripTrailingZeros()
        .toPlainString();
    String totalPriceStr = dao.getTotalPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
        .toPlainString();
    String state = dao.getState();

    if (dao.getItemUid() == null) {
      priceStr = "-";
      interestPriceStr = "-";
      totalPriceStr = "-";
      state = "-";
    }

    return MarketItemAdminVipTokenStakingResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .vipFlag(dao.isVipFlag())
        .closeFlag(dao.isCloseFlag())
        .round(dao.getRound())
        .closeRound(dao.getCloseRound())
        .interest(dao.getInterest())
        .startAt(dao.getStartAt())
        .endAt(dao.getEndAt())
        .requestEndAt(dao.getRequestEndAt())
        .withdrawalRequestEndAt(dao.getWithdrawalRequestEndAt())
        .minPrice(dao.getMinPrice().setScale(3, RoundingMode.DOWN))
        .minPriceStr(
            dao.getMinPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros().toPlainString())
        .price(dao.getPrice().setScale(3, RoundingMode.DOWN))
        .priceStr(priceStr)
        .interestPrice(dao.getInterestPrice().setScale(3, RoundingMode.DOWN))
        .interestPriceStr(interestPriceStr)
        .totalPrice(dao.getTotalPrice().setScale(3, RoundingMode.DOWN))
        .totalPriceStr(totalPriceStr)
        .itemKind(dao.getItemKind())
        .pointAbleFlag(dao.isPointAbleFlag())
        .state(state)
        .itemUid(dao.getItemUid())
        .referrerCode(dao.getReferrerCode())
        .countReferrer(dao.getCountReferrer())
        .userId(dao.getUserId())
        .build();

  }


  private MarketItemListResponseModel covertMarketItemListResponseModel(ItemDetailDao dao,
      MarketItemListRequestModel requestModel) {

    ItemBuyListRequestModel itemBuyListRequestModel = new ItemBuyListRequestModel();
    itemBuyListRequestModel.setMemberUid(requestModel.getMemberUid());
    itemBuyListRequestModel.setItemUid(dao.getUid());
    itemBuyListRequestModel.setUserId(requestModel.getUserId());
    //itemBuyListRequestModel.setYear(Integer.parseInt(requestModel.getYear()));
    List<ItemBuyListResponseModel> buyList = this.listItemBuy(itemBuyListRequestModel);

    BigDecimal totalEthPrice;
    BigDecimal totalMEthPrice;

    BigDecimal totalInterestPrice;

    String totalDepositPriceStr = "0.000";

    if (dao.getTotalDepositPrice() != null) {
      totalDepositPriceStr = dao.getTotalDepositPrice().setScale(3, RoundingMode.DOWN)
          .toPlainString();
    }

    totalEthPrice = buyList.stream().filter(v -> v.getPriceUnit().equals(PriceUnit.ETH.name()))
        .map(ItemBuyListResponseModel::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    totalMEthPrice = buyList.stream().filter(v -> v.getPriceUnit().equals(PriceUnit.mETH.name()))
        .map(ItemBuyListResponseModel::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    totalInterestPrice = buyList.stream().map(ItemBuyListResponseModel::getInterestPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    ItemBuySettlementListRequestModel itemBuySettlementListRequestModel = new ItemBuySettlementListRequestModel();
    itemBuySettlementListRequestModel.setItemUid(dao.getUid());
    itemBuySettlementListRequestModel.setMemberUid(requestModel.getMemberUid());
    itemBuySettlementListRequestModel.setUserId(requestModel.getUserId());
    List<ItemBuySettlementListResponseModel> settlementList = this.listItemBuySettlement(
        itemBuySettlementListRequestModel);
    BigDecimal totalWithdrawalPrice = settlementList.stream()
        .filter(v -> v.getType().equals(SettlementType.WITHDRAWAL.name()))
        .map(ItemBuySettlementListResponseModel::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    return MarketItemListResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .vipFlag(dao.isVipFlag())
        .closeFlag(dao.isCloseFlag())
        .round(dao.getRound())
        .closeRound(dao.getCloseRound())
        .interest(dao.getInterest())
        .startAt(dao.getStartAt())
        .endAt(dao.getEndAt())
        .requestEndAt(dao.getRequestEndAt())
        .withdrawalRequestEndAt(dao.getWithdrawalRequestEndAt())
        .minPrice(dao.getMinPrice())
        .totRequestEthPrice(totalEthPrice)
        .totRequestEthPriceStr(totalEthPrice.setScale(3,
            RoundingMode.DOWN).toPlainString())
        .totRequestMEthPrice(totalMEthPrice)
        .totRequestMEthPriceStr(totalMEthPrice.setScale(3,
            RoundingMode.DOWN).toPlainString())
        .totInterestPrice(totalInterestPrice)
        .totInterestPriceStr(totalInterestPrice.setScale(3, RoundingMode.DOWN).toPlainString())
        .totWithdrawalPrice(totalWithdrawalPrice)
        .totWithdrawalPriceStr(totalWithdrawalPrice.setScale(3, RoundingMode.DOWN).toPlainString())
        .totDepositPrice(dao.getTotalDepositPrice())
        .totDepositPriceStr(totalDepositPriceStr)
        .buyList(buyList)
        .settlementList(settlementList)
        .itemKind(dao.getItemKind())
        .autoItemUid(dao.getAutoItemUid())
        .build();
  }

  private MarketItemByReferrerResponseModel covertMarketItemByReferrerResponseModel(
      MarketItemByReferrerDao dao) {

    return MarketItemByReferrerResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .vipFlag(dao.isVipFlag())
        .closeFlag(dao.isCloseFlag())
        .round(dao.getRound())
        .closeRound(dao.getCloseRound())
        .interest(dao.getInterest())
        .startAt(dao.getStartAt())
        .endAt(dao.getEndAt())
        .requestEndAt(dao.getRequestEndAt())
        .withdrawalRequestEndAt(dao.getWithdrawalRequestEndAt())
        .minPrice(dao.getMinPrice())
        .itemKind(dao.getItemKind())
        //.totalPrice(dao.getTotalPrice())
        //.totalPriceStr(dao.getTotalPrice().setScale(3, RoundingMode.FLOOR).toPlainString())
        .pointAbleFlag(dao.isPointAbleFlag())
        .build();
  }

  private MarketItemMyReferrerResponseModel convertMarketItemMyReferrerResponseModel(
      MarketItemMyReferrerDao dao) {

    return MarketItemMyReferrerResponseModel.builder()
        .referrerCode(dao.getReferrerCode())
        .methPrice(dao.getMethPrice())
        .methPriceStr(dao.getMethPrice().setScale(3, RoundingMode.DOWN).toPlainString())
        .totalPrice(dao.getTotalPrice())
        .totalPriceStr(
            dao.getTotalPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros().toPlainString())
        .email(dao.getEmail())
        .interest(dao.getInterest())
        .interestPrice(dao.getInterestPrice().setScale(3, RoundingMode.DOWN))
        .interestPriceStr(dao.getInterestPrice().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
            .toPlainString())
        .pointAbleFlag(dao.isPointAbleFlag())
        .build();

  }

  private MarketItemListResponseModel covertMarketItemListResponseModel(ItemDetailDao dao) {

    return MarketItemListResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .vipFlag(dao.isVipFlag())
        .closeFlag(dao.isCloseFlag())
        .round(dao.getRound())
        .closeRound(dao.getCloseRound())
        .interest(dao.getInterest())
        .startAt(dao.getStartAt())
        .endAt(dao.getEndAt())
        .requestEndAt(dao.getRequestEndAt())
        .minPrice(dao.getMinPrice())
        .build();
  }


  private ItemBuyListResponseModel convertItemBuyListResponseModel(ItemBuyDetailDao dao) {
    return ItemBuyListResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .curWithdrawalRequestEndAt(dao.getCurWithdrawalRequestEndAt())
        .memberUid(dao.getMemberUid())
        .price(dao.getPrice())
        .krwPrice(dao.getKrwPrice())
        .interestPrice(dao.getInterestPrice())
        .krwInterestPrice(dao.getKrwInterestPrice())
        .priceStr(dao.getPrice().setScale(3, RoundingMode.DOWN).toPlainString())
        .krwPriceStr(dao.getKrwPrice().setScale(3, RoundingMode.DOWN).toPlainString())
        .interestPriceStr(dao.getInterestPrice().setScale(3, RoundingMode.DOWN).toPlainString())
        .krwInterestPriceStr(
            dao.getKrwInterestPrice().setScale(3, RoundingMode.DOWN).toPlainString())
        .startAt(dao.getStartAt())
        .endAt(dao.getEndAt())
        .requestEndAt(dao.getRequestEndAt())
        .withdrawalRequestEndAt(dao.getWithdrawalRequestEndAt())
        .round(dao.getRound())
        .closeRound(dao.getCloseRound())
        .interest(dao.getInterest())
        .priceUnit(dao.getPriceUnit())
        .remainingDays(dao.getRemainingDays())
        .itemUid(dao.getItemUid())
        .build();

  }

  private ItemBuySettlementListResponseModel convertItemBuySettlementListResponseModel(
      ItemBuySettlementDao dao) {
    return ItemBuySettlementListResponseModel.builder()
        .uid(dao.getUid())
        .itemUid(dao.getItemUid())
        .memberUid(dao.getMemberUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .type(dao.getType())
        .price(dao.getPrice())
        .withdrawalApprovalFlag(dao.isWithdrawalApprovalFlag())
        .withdrawalCompletedFlag(dao.isWithdrawalCompletedFlag())
        .build();
  }

}
