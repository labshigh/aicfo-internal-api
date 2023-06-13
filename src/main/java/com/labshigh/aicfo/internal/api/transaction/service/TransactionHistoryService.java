package com.labshigh.aicfo.internal.api.transaction.service;

import com.labshigh.aicfo.core.models.ResponseListModel;
import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.utils.TelegramUtils;
import com.labshigh.aicfo.internal.api.member.dao.MemberDao;
import com.labshigh.aicfo.internal.api.member.mapper.MemberMapper;
import com.labshigh.aicfo.internal.api.transaction.dao.TransactionEventDao;
import com.labshigh.aicfo.internal.api.transaction.dao.TransactionHistoryDao;
import com.labshigh.aicfo.internal.api.transaction.mapper.TransactionEventMapper;
import com.labshigh.aicfo.internal.api.transaction.mapper.TransactionHistoryMapper;
import com.labshigh.aicfo.internal.api.transaction.model.request.TransactionEventWalletInsertRequestModel;
import com.labshigh.aicfo.internal.api.transaction.model.request.TransactionHistoryInsertRequestModel;
import com.labshigh.aicfo.internal.api.transaction.model.request.TransactionHistoryRequestModel;
import com.labshigh.aicfo.internal.api.transaction.model.request.TransactionHistoryWalletInsertRequestModel;
import com.labshigh.aicfo.internal.api.transaction.model.response.TransactionHistoryResponseModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransactionHistoryService {


  private final TransactionHistoryMapper transactionHistoryMapper;

  private final TransactionEventMapper transactionEventMapper;
  private final MemberMapper memberMapper;

  private final TelegramUtils telegramUtils;

  @Transactional
  public void insert(TransactionHistoryInsertRequestModel request) {

    transactionHistoryMapper.insert(TransactionHistoryDao.builder()
        .price(request.getPrice())
        .unit(request.getUnit())
        .memberUid(request.getMemberUid())
        .transactionKindUid(request.getTransactionKindUid())
        .transactionUid(request.getTransactionUid())
        .txHash(request.getTxHash())
        .build());
  }

  public void insertWebhook(TransactionHistoryWalletInsertRequestModel request) {

    transactionHistoryMapper.insert(TransactionHistoryDao.builder()
        .price(new BigDecimal(request.getPrice()))
        .unit("ETH")
        .memberUid(Long.parseLong(request.getUserId()))
        .transactionKindUid(request.getTransactionKindUid())
        .transactionUid(request.getTransactionUid())
        .build());
  }

  public ResponseListModel list(TransactionHistoryRequestModel request) {

    ResponseListModel result = new ResponseListModel();

    int totalCount = transactionHistoryMapper.count(request);

    result.setCurrentPage(request.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(request.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;
    }

    List<TransactionHistoryDao> listDao = transactionHistoryMapper.list(request);
    List<TransactionHistoryResponseModel> list = listDao.stream()
        .map(this::convertTransactionHistoryResponseModel)
        .collect(Collectors.toList());

    result.setList(list);

    return result;

  }

  private TransactionHistoryResponseModel convertTransactionHistoryResponseModel(
      TransactionHistoryDao dao) {
    return TransactionHistoryResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .price(dao.getPrice())
        .unit(dao.getUnit())
        .memberUid(dao.getMemberUid())
        .transactionKindUid(dao.getTransactionKindUid())
        .transactionKindName(dao.getTransactionKindName())
        .transactionUid(dao.getTransactionUid())
        .userId(dao.getUserId())
        .txHash(dao.getTxHash())
        .build();
  }

  public ResponseModel sendTelegram(String msg) throws InterruptedException {
    return telegramUtils.postSend(msg);
  }

}
