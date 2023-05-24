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
import com.labshigh.aicfo.internal.api.wallet.dao.MemberWalletDao;
import com.labshigh.aicfo.internal.api.wallet.mapper.MemberWalletMapper;
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
  private final MemberWalletMapper memberWalletMapper;
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

  public void insertEvent(TransactionEventWalletInsertRequestModel request) {
    MemberWalletDao memberWalletDao = memberWalletMapper.get(
        MemberWalletDao.builder().userId(request.getUserId()).build());

    BigDecimal amount = BigDecimal.valueOf(request.getAmount()).setScale(3, RoundingMode.DOWN);
    TransactionEventDao transactionEventDao = TransactionEventDao.builder()
        .userId(request.getUserId())
        .tokenId(request.getTokenId())
        .tokenSymbol(request.getTokenSymbol())
        .transactionId(request.getTransactionId())
        .amount(amount)
        .fromAddress(request.getFromAddress())
        .fromUserId(request.getFromUserId())
        .toAddress(request.getToAddress())
        .toUserId(request.getToUserId())
        .transactionType(request.getTransactionType())
        .transactionStatus(request.getTransactionStatus())
        .txHash(request.getTxHash())
        .memberUid(memberWalletDao.getMemberUid())
        .build();

    transactionEventMapper.insert(transactionEventDao);
    if ("II".equals(request.getTransactionType()) || "EI".equals(request.getTransactionType())) {
      long transactionKindUid =15;
       if( "EI".equals(request.getTransactionType() ))
       {
         transactionKindUid =16;
       }

      transactionHistoryMapper.insert(TransactionHistoryDao.builder()
          .price(amount)
          .unit("ETH")
          .memberUid(memberWalletDao.getMemberUid())
          .transactionKindUid(transactionKindUid)
          .transactionUid(request.getTransactionId())
          .userId(request.getUserId())
          .txHash(request.getTxHash())
          .build());
      if( "EI".equals(request.getTransactionType() )){
        // 텔레그렘 외부 입금 메세지
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endAt = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), 0, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        MemberDao memberDao = memberMapper.get(MemberDao.builder()
          .uid(memberWalletDao.getMemberUid()).build());
        String msg = memberDao.getEmail();
        if( !StringUtils.isEmpty(memberDao.getNickname()) ){
          msg = msg + "("+memberDao.getNickname() +")";
        }
        msg = msg + " "+amount+" ETH ("+now.format(formatter).toString() +") 입금";
        try {
          this.sendTelegram(msg);
        } catch (InterruptedException e) {
          log.error(e.getMessage());
        }
      }

    }





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
