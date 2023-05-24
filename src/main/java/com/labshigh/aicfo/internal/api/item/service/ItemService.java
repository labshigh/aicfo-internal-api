package com.labshigh.aicfo.internal.api.item.service;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.common.utils.FileUploadUtils;
import com.labshigh.aicfo.internal.api.item.dao.BasicItemDao;
import com.labshigh.aicfo.internal.api.item.dao.ItemDao;
import com.labshigh.aicfo.internal.api.item.mapper.ItemFileMapper;
import com.labshigh.aicfo.internal.api.item.mapper.ItemMapper;
import com.labshigh.aicfo.internal.api.item.model.request.ItemInsertRequestModel;
import com.labshigh.aicfo.internal.api.item.model.request.ItemListRequestModel;
import com.labshigh.aicfo.internal.api.item.model.request.ItemSortUpdateRequestModel;
import com.labshigh.aicfo.internal.api.item.model.request.MarketItemInsertRequestModel;
import com.labshigh.aicfo.internal.api.item.model.response.ItemListResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.dao.MarketItemDao;
import com.labshigh.aicfo.internal.api.marketItem.mapper.ItemBuyMapper;
import com.labshigh.aicfo.internal.api.marketItem.mapper.MarketItemDetailMapper;
import com.labshigh.aicfo.internal.api.marketItem.mapper.MarketItemMapper;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class ItemService {

  @Value("${ncloud.object-storage.end-point}")
  private String s3EndPoint;
  @Value("${ncloud.object-storage.bucket}")
  private String s3NftBucket;


  @Autowired
  private ItemMapper itemMapper;
  @Autowired
  private ItemFileMapper itemFileMapper;
  @Autowired
  private MarketItemMapper marketItemMapper;

  @Autowired
  private MarketItemDetailMapper marketItemDetailMapper;

  @Autowired
  private ItemBuyMapper itemBuyMapper;

  @Autowired
  private FileUploadUtils fileUploadUtils;

  public List<ItemListResponseModel> list(ItemListRequestModel requestModel) {
//    ResponseListModel result = new ResponseListModel();

//    int totalCount = itemMapper.count(requestModel);
//
//    result.setCurrentPage(requestModel.getPage());
//    result.setTotalCount(totalCount);
//    result.setPageSize(requestModel.getSize());
//
//    if (totalCount < 1) {
//      result.setList(Collections.emptyList());
//      return result;
//    }

    return itemMapper.list(requestModel).stream()
        .map(this::convertItemListResponseModel).collect(Collectors.toList());
  }

  @Transactional
  public ItemListResponseModel insert(ItemInsertRequestModel requestModel) throws IOException {

    ItemDao dao = ItemDao.builder()
        .vipFlag(requestModel.isVipFlag())
        .round(requestModel.getRound())
        .closeRound(requestModel.getCloseRound())
        .interest(requestModel.getInterest())
        .startAt(requestModel.getStartAt())
        .endAt(requestModel.getEndAt())
        .requestEndAt(requestModel.getRequestEndAt())
        .withdrawalRequestEndAt(requestModel.getWithdrawalRequestEndAt())
        .minPrice(requestModel.getMinPrice())
        .autoItemUid(requestModel.getAutoItemUid())
        .itemKind(requestModel.getItemKind())
        .build();

    itemMapper.insert(dao);
    convertItemListResponseModel(dao);
//
//    itemMapper.updateSort();
//
//    ItemDao dao = ItemDao.builder()
//        .vipFlag(requestModel.isVipFlag())
//        .imageUri(requestModel.getImageUri())
//        .name(requestModel.getName())
//        .price(requestModel.getPrice())
//        .quantity(requestModel.getQuantity())
//        .currentQuantity(requestModel.getCurrentQuantity())
//        .tokenUri(requestModel.getTokenUri())
//        .itemKind(requestModel.getItemKind())
//        .midSaleFlag(requestModel.isMidSaleFlag())
//        .sort(requestModel.getSort())
//        .interest(requestModel.getInterest())
//        .description(requestModel.getDescription())
//        .startAt(requestModel.getStartAt())
//        .durationId(requestModel.getDurationId())
//        .adminWalletUid(requestModel.getAdminWalletUid())
//        .build();
//
//    ItemMetaDataModel metaDataModel = ItemMetaDataModel.builder()
//        .imageUri(requestModel.getImageUri())
//        .name(requestModel.getName())
//        .midSaleFlag(requestModel.isMidSaleFlag())
//        .interest(requestModel.getInterest())
//        .description(requestModel.getDescription())
//        .startAt(requestModel.getStartAt())
//        .build();
//
//    String pathName = requestModel.getImageUri()
//        .replace("https://" + s3EndPoint, "").split("/")[2];
//    //metadata.Json 업로드
//    String tokenUri = fileUploadUtils.uploadByObject(metaDataModel, pathName, FileType.nft);
//
//    dao.setTokenUri(tokenUri.replace("https://" + s3EndPoint, ""));
//
//    dao.setImageUri(
//        requestModel.getImageUri().replace("https://" + s3EndPoint, ""));
//
//    itemMapper.insert(dao);

//    return convertItemListResponseModel(dao);
    return null;
  }

  @Transactional
  public MarketItemDao insertMarketItem(MarketItemInsertRequestModel requestModel) {
//    ItemListRequestModel itemListRequestModel = new ItemListRequestModel();
//    itemListRequestModel.setUid(requestModel.getItemUid());
//
//    List<ItemDao> itemDaoList = itemMapper.list(itemListRequestModel);
//
//    if (itemDaoList.isEmpty()) {
//      throw new ServiceException(Constants.MSG_NO_DATA);
//    }
//
//    ItemDao dao = itemDaoList.get(0);
//
//    if (dao.getAdminWalletUid() != requestModel.getAdminWalletUid()) {
//      throw new ServiceException(Constants.MSG_ITEM_MEMBER_ERROR);
//    }
//
//    MarketItemDao marketItemDao = MarketItemDao.builder()
//        .itemUid(dao.getUid())
//        .quantity(dao.getQuantity())
//        .currentQuantity(dao.getQuantity())
//        .price(requestModel.getPrice())
//        .transactionHash(requestModel.getTransactionHash())
//        .build();
//
//    marketItemMapper.insert(marketItemDao);
//    return marketItemDao;
    return null;
  }

  @Transactional
  public void updateSortByAdmin(ItemSortUpdateRequestModel requestModel) {

//    if (requestModel.getSortValue().equals("false")) {
//      marketItemMapper.updateUseItem(
//          MarketItemDao.builder().itemUid(requestModel.getUid()).build());
//
//    } else {
//      ItemDao dao = ItemDao.builder().uid(requestModel.getUid())
//          .sort(Integer.parseInt(requestModel.getSortValue())).build();
//      itemMapper.updateSortByAdmin(dao);
//    }
  }


  private ItemListResponseModel convertItemListResponseModel(ItemDao dao) {
    if (dao == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    return ItemListResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .vipFlag(dao.isVipFlag())
        .startAt(dao.getStartAt())
        .round(dao.getRound())
        .closeRound(dao.getCloseRound())
        .interest(dao.getInterest())
        .endAt(dao.getEndAt())
        .requestEndAt(dao.getRequestEndAt())
        .minPrice(dao.getMinPrice())
        .build();
  }

  @Transactional
  public void initItem() {
    List<BasicItemDao> basicDaoList = itemMapper.selectBasicItem();

    List<ItemDao> daoList = new ArrayList<>();

    int year = LocalDate.now().getYear();
    int maxYear = year + 100;

    for (int y = year; y <= maxYear; y++) {
      int round = 1;
      for (int m = 1; m < 13; m++) {
        for (BasicItemDao basicItem : basicDaoList) {

          int day = basicItem.getDay();

          if (m == 2 && day == 30) {
            day = 28;
          }

          LocalDateTime startAt = LocalDateTime.of(y, m, day, 17, 0);
          int closeRound = round + basicItem.getCloseRound();

          if (closeRound > 36) {
            closeRound = closeRound - 36;
          }

          daoList.add(ItemDao.builder()
              .vipFlag(true)
              .round(Integer.toString(round))
              .closeRound(Integer.toString(closeRound))
              .interest(basicItem.getInterest())
              .startAt(startAt)
              .requestEndAt(startAt.plusDays(basicItem.getStakingRequestDay()))
              .minPrice(basicItem.getMinPrice())
              .build()
          );
          round++;
        }

      }
    }
    //종료일자
    for (ItemDao dao : daoList) {
      Optional<ItemDao> itemDaoOptional = daoList.stream()
          .filter(d -> d.getRound().equals(dao.getCloseRound())
              && d.getStartAt().getYear() == dao.getStartAt().getYear()).findAny();

      if (itemDaoOptional.isPresent()) {
        LocalDateTime endAt = itemDaoOptional.get().getStartAt();

        if (Integer.parseInt(dao.getRound()) > 31) {
          endAt = endAt.plusYears(1);
        }
        dao.setEndAt(endAt);
      }

      itemMapper.insert(dao);

    }


  }


}
