package com.labshigh.aicfo.internal.api.wallet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.labshigh.aicfo.core.helper.WebClientHelper;
import com.labshigh.aicfo.core.inherits.AbstractRestService;
import com.labshigh.aicfo.internal.api.common.ApiPathConstants;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.member.dao.MemberDao;
import com.labshigh.aicfo.internal.api.member.mapper.MemberMapper;
import com.labshigh.aicfo.internal.api.member.model.request.MemberWithdrawalWalletSearchRequestModel;
import com.labshigh.aicfo.internal.api.wallet.dao.MemberMEthDao;
import com.labshigh.aicfo.internal.api.wallet.dao.MemberWalletAdminDao;
import com.labshigh.aicfo.internal.api.wallet.dao.MemberWalletAdminTotalBalanceDao;
import com.labshigh.aicfo.internal.api.wallet.dao.MemberWalletDao;
import com.labshigh.aicfo.internal.api.wallet.dao.MemberWalletWithdrawalDao;
import com.labshigh.aicfo.internal.api.wallet.dao.WalletTransactionDao;
import com.labshigh.aicfo.internal.api.wallet.mapper.MemberMEthMapper;
import com.labshigh.aicfo.internal.api.wallet.mapper.MemberWalletMapper;
import com.labshigh.aicfo.internal.api.wallet.model.request.MemberWalletAdminRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.request.MemberWalletRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.request.MemberWithdrawalWalletRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.request.WalletBalanceRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.request.WalletMEthTransactionPutRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.request.WalletTransactionPutRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.request.WalletTransactionWaitRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.response.MemberWalletAdminResponseModel;
import com.labshigh.aicfo.internal.api.wallet.model.response.MemberWalletAdminTotalBalanceResponseModel;
import com.labshigh.aicfo.internal.api.wallet.model.response.MemberWalletMyTokenModel;
import com.labshigh.aicfo.internal.api.wallet.model.response.MemberWalletMyTokensResponseModel;
import com.labshigh.aicfo.internal.api.wallet.model.response.MemberWalletResponseModel;
import com.labshigh.aicfo.internal.api.wallet.model.response.MemberWalletTokenModel;
import com.labshigh.aicfo.internal.api.wallet.model.response.MemberWalletTokensResponseModel;
import com.labshigh.aicfo.internal.api.wallet.model.response.MemberWalletWithdrawalResponseModel;
import com.labshigh.aicfo.internal.api.wallet.model.response.WalletBalanceResponseModel;
import com.labshigh.aicfo.internal.api.wallet.model.response.WalletTransactionResponseModel;
import com.labshigh.aicfo.internal.api.withdraw.dao.WithdrawDao;
import com.labshigh.aicfo.internal.api.withdraw.mapper.WithdrawMapper;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.naming.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Log4j2
@Service
@RequiredArgsConstructor
public class MemberWalletService extends AbstractRestService {


  private final MemberWalletMapper memberWalletMapper;
  private final WithdrawMapper withdrawMapper;
  private final MemberMapper memberMapper;
  private final ObjectMapper objectMapper;
  private final MemberMEthMapper memberMEthMapper;

  @Value("${aicfo.wallet.api}")
  private String walletApi;

  @Transactional
  public MemberWalletResponseModel insertMemberWallet(
      MemberWalletRequestModel memberWalletRequestModel) throws AuthenticationException {
    MemberWalletDao memberWalletDao = null;

    // 이미 발급된 월렛이 있는지 체크
    try {
      memberWalletDao = memberWalletMapper.get(
          MemberWalletDao.builder()
              .memberUid(memberWalletRequestModel.getMemberUid())
              .tokenId(memberWalletRequestModel.getTokenId())
              .coinId(memberWalletRequestModel.getCoinId())
//              .userId(memberWalletRequestModel.getUserId())
              .build());
      if (memberWalletDao.getAddress() != null) {
        throw new ServiceException(String.format(Constants.MSG_DUPLICATED_DATA));
      }
    } catch (Exception e) {
      throw new ServiceException(String.format(Constants.MSG_DUPLICATED_DATA));
    }

    MemberWalletResponseModel memberWalletResponseModel = null;
    // 월렛발급
    String responseModel = null;
    try {

      URI uri = makeUri(walletApi, ApiPathConstants.API_PATH_WALLET_ADDRESS);
      responseModel = WebClientHelper.getInstance()
          .post(uri, memberWalletRequestModel, String.class);

      memberWalletResponseModel = objectMapper.readValue(responseModel,
          MemberWalletResponseModel.class);
      memberWalletResponseModel.setMemberUid(memberWalletRequestModel.getMemberUid());
      memberWalletMapper.updateWalletAddress(
          MemberWalletDao.builder()
              .accountId(memberWalletResponseModel.getAccountId())
              .address(memberWalletResponseModel.getAddress())
              .userId(memberWalletRequestModel.getUserId())
              .uid(memberWalletDao.getUid())
              .build());

      int imageSize = 200;
      BitMatrix matrix = new MultiFormatWriter().encode(memberWalletResponseModel.getAddress(),
          BarcodeFormat.QR_CODE,
          imageSize, imageSize);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      MatrixToImageWriter.writeToStream(matrix, "png", bos);
      String qrCode = Base64.getEncoder().encodeToString(bos.toByteArray()); // base64 encode
      memberWalletResponseModel.setQrCode(qrCode);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage(), e.hashCode());
    }
    return memberWalletResponseModel;
  }

  @Transactional
  public MemberWalletWithdrawalResponseModel insertMemberWithdrawalWallet(
      MemberWithdrawalWalletRequestModel memberWithdrawalWalletRequestModel)
      throws AuthenticationException {

    // 이미 등록된 출금 월렛이 있는지 체크
    MemberWalletWithdrawalDao memberWalletWithdrawalDao = memberWalletMapper.getWithdrawalWallet(
        MemberWalletWithdrawalDao.builder()
            .memberUid(memberWithdrawalWalletRequestModel.getMemberUid())
            .tokenId(memberWithdrawalWalletRequestModel.getTokenId())
            .coinId(memberWithdrawalWalletRequestModel.getCoinId())
            .address(memberWithdrawalWalletRequestModel.getAddress())
            .build());
    if (memberWalletWithdrawalDao != null) {
      throw new ServiceException(String.format(Constants.MSG_DUPLICATED_DATA));
    }
    // 내부계좌 확인
    MemberWalletDao memberWalletDao = memberWalletMapper.get(MemberWalletDao.builder()
        .address(memberWithdrawalWalletRequestModel.getAddress())
        .build());

    boolean internalWalletFlag = false;
    if (null != memberWalletDao) {
      internalWalletFlag = true;
    }

    // 출금 월렛 등록
    memberWalletWithdrawalDao = MemberWalletWithdrawalDao.builder()
        .memberUid(memberWithdrawalWalletRequestModel.getMemberUid())
        .name(memberWithdrawalWalletRequestModel.getName())
        .address(memberWithdrawalWalletRequestModel.getAddress())
        .coinId(memberWithdrawalWalletRequestModel.getCoinId())
        .tokenId(memberWithdrawalWalletRequestModel.getTokenId())
        .internalWalletFlag(internalWalletFlag)
        .build();

    memberWalletMapper.insertWithdrawalWallet(memberWalletWithdrawalDao);

    return MemberWalletWithdrawalResponseModel.builder()
        .uid(memberWalletWithdrawalDao.getUid())
        .name(memberWalletWithdrawalDao.getName())
        .address(memberWalletWithdrawalDao.getAddress())
        .coinId(memberWalletWithdrawalDao.getCoinId())
        .tokenId(memberWalletWithdrawalDao.getTokenId())
        .memberUid(memberWalletWithdrawalDao.getMemberUid())
        .internalWalletFlag(memberWalletWithdrawalDao.isInternalWalletFlag())
        .build();
  }


  public MemberWalletWithdrawalResponseModel getMemberWithdrawalWalletCheck(String address)
      throws AuthenticationException {

    // 내부계좌 확인
    MemberWalletDao memberWalletDao = memberWalletMapper.get(MemberWalletDao.builder()
        .address(address)
        .build());
    String email = null;
    String code = null;
    boolean internalWalletFlag = false;
    if (null != memberWalletDao) {
      internalWalletFlag = true;
      MemberDao memberDao = memberMapper.get(MemberDao.builder()
          .uid(memberWalletDao.getMemberUid())
        .build());
      email = memberDao.getEmail();
      String[] emailArr = email.split("@");
      email = emailArr[0].substring(0,3) +"****@"+emailArr[1];
      code = memberWalletDao.getReferrerCode().substring(0,3)+"****";
    }

    return MemberWalletWithdrawalResponseModel.builder()
        .internalWalletFlag(internalWalletFlag)
        .email(email)
        .referrerCode(code)
        .build();
  }

  public List<MemberWalletDao> getMemberWalletList(Long memberUid) throws Exception {

    MemberWalletDao memberWalletDao = MemberWalletDao.builder()
        .memberUid(memberUid)
        .build();
    List<MemberWalletDao> memberWalletDaoList = memberWalletMapper.getList(memberWalletDao);
    List<MemberWalletWithdrawalResponseModel> memberWalletWithdrawalResponseModelList = new ArrayList<>();

    return memberWalletDaoList;
  }

  public MemberWalletDao getMemberWallet(Long memberUid, Long walletId) throws Exception {

    MemberWalletDao memberWalletDao = MemberWalletDao.builder()
        .memberUid(memberUid)
        .uid(walletId)
        .build();
    memberWalletDao = memberWalletMapper.get(memberWalletDao);

    return memberWalletDao;
  }

  public List<MemberWalletAdminResponseModel> getAdminMemberWallet(
      MemberWalletAdminRequestModel requestModel) {

    List<MemberWalletAdminDao> list = memberWalletMapper.getAdminMemberWallet(requestModel);

    return list.stream().map(this::convertMemberWalletAdminResponseModel)
        .collect(Collectors.toList());
  }

  @Transactional
  public MemberWalletDao postMEthTransactionsPut(WalletMEthTransactionPutRequestModel requestModel)
      throws AuthenticationException {

    MemberWalletDao memberWalletDao = null;

    if (requestModel.getMemberUid() <= 0) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }

    if (requestModel.getWalletId() <= 0) {
      throw new ServiceException(Constants.MSG_MEMBER_WALLET_NO);
    }

    String responseModel = null;
    try {
      memberWalletDao = getMemberWallet(requestModel.getMemberUid(), requestModel.getWalletId());
      if (requestModel.getMethUseAmount().doubleValue() < 0) {
        if ((memberWalletDao.getMEth().add(requestModel.getMethUseAmount())
            .compareTo(new BigDecimal("0"))) == -1) {
          throw new ServiceException(Constants.MSG_WALLET_INSUFFICIENT_BALANCE);
        }
      }

      // mETH 수량
      BigDecimal meth = memberWalletDao.getMEth().add(requestModel.getMethUseAmount());
      memberWalletMapper.updateMEth(MemberWalletDao.builder()
          .mEth(meth)
          .uid(requestModel.getWalletId())
          .build());

      // 로그 추가
      MemberMEthDao memberMEthDao = MemberMEthDao.builder()
          .methAmount(meth)
          .methType(requestModel.getMethType())
          .methUseAmount(requestModel.getMethUseAmount())
          .memo(requestModel.getMemo())
          .memberUid(requestModel.getMemberUid())
          .walletId(requestModel.getWalletId())
          .build();
      memberMEthMapper.insertMemberMEth(memberMEthDao);
    } catch (Exception e) {
      throw new ServiceException(Constants.MSG_WALLET_INSUFFICIENT_BALANCE);
    }

    return memberWalletDao;
  }

  @Transactional
  public void deleteMemberWithdrawalWallet(
      MemberWithdrawalWalletRequestModel memberWithdrawalWalletRequestModel)
      throws AuthenticationException {
    WithdrawDao withdrawDao = withdrawMapper.getWalletRequest(WithdrawDao.builder()
        .memberWalletWithdrawalUid(memberWithdrawalWalletRequestModel.getUid())
        .build());

    // 출금 요청중인 건이 있으면 삭제 불가
    if (null != withdrawDao) {
      throw new ServiceException(Constants.MSG_MEMBER_WALLET_NOT_REQUEST);
    }

    // 출금 월렛 삭제
    MemberWalletWithdrawalDao memberWalletWithdrawalDao = MemberWalletWithdrawalDao.builder()
        .memberUid(memberWithdrawalWalletRequestModel.getMemberUid())
        .uid(memberWithdrawalWalletRequestModel.getUid())
        .name(memberWithdrawalWalletRequestModel.getName())
        .address(memberWithdrawalWalletRequestModel.getAddress())
        .coinId(memberWithdrawalWalletRequestModel.getCoinId())
        .tokenId(memberWithdrawalWalletRequestModel.getTokenId())
        .build();

    memberWalletMapper.deleteWithdrawalWallet(memberWalletWithdrawalDao);

  }

  public List<MemberWalletWithdrawalResponseModel> getMemberWithdrawalWalletList(
      MemberWithdrawalWalletSearchRequestModel requestModel) throws Exception {

    List<MemberWalletWithdrawalDao> MemberWalletWithdrawalDaoList = memberWalletMapper.getWithdrawalWalletList(
        requestModel);
    List<MemberWalletWithdrawalResponseModel> memberWalletWithdrawalResponseModelList = new ArrayList<>();

    MemberWalletWithdrawalDaoList.forEach(memberWalletWithdrawalDao -> {


      int imageSize = 200;
      try {
        String email = null;
        String code = null;
        // 내부계좌 확인
        MemberWalletDao memberWalletDao = memberWalletMapper.get(MemberWalletDao.builder()
          .address(memberWalletWithdrawalDao.getAddress())
          .build());
        if (null != memberWalletDao) {

            MemberDao memberDao = memberMapper.get(MemberDao.builder()
              .uid(memberWalletDao.getMemberUid())
              .build());
            email = memberDao.getEmail();
            String[] emailArr = email.split("@");
            email = emailArr[0].substring(0,3) +"****@"+emailArr[1];
            code = memberWalletDao.getReferrerCode().substring(0,3)+"****";
        }

        BitMatrix matrix = new MultiFormatWriter().encode(memberWalletWithdrawalDao.getAddress(),
            BarcodeFormat.QR_CODE,
            imageSize, imageSize);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "png", bos);
        String qrCode = Base64.getEncoder().encodeToString(bos.toByteArray());

        MemberWalletWithdrawalResponseModel memberWalletWithdrawalResponseModel =
            MemberWalletWithdrawalResponseModel.builder()
                .uid(memberWalletWithdrawalDao.getUid())
                .createdAt(memberWalletWithdrawalDao.getCreatedAt())
                .updatedAt(memberWalletWithdrawalDao.getUpdatedAt())
                .email(email)
                .referrerCode(code)
                .uid(memberWalletWithdrawalDao.getUid())
                .name(memberWalletWithdrawalDao.getName())
                .address(memberWalletWithdrawalDao.getAddress())
                .coinId(memberWalletWithdrawalDao.getCoinId())
                .tokenId(memberWalletWithdrawalDao.getTokenId())
                .memberUid(memberWalletWithdrawalDao.getMemberUid())
                .internalWalletFlag(memberWalletWithdrawalDao.isInternalWalletFlag())
                .qrCode(qrCode)
                .internalWalletFlag(memberWalletWithdrawalDao.isInternalWalletFlag())
                .history(
                    memberWalletMapper.withdrawalHistory(memberWalletWithdrawalDao.getMemberUid(),
                        memberWalletWithdrawalDao.getAddress()))
                .build();
        memberWalletWithdrawalResponseModelList.add(memberWalletWithdrawalResponseModel);
      } catch (Exception e) {
        log.debug(e.getMessage());
      }
    });

    return memberWalletWithdrawalResponseModelList;
  }

  public MemberWalletTokensResponseModel getTokens() throws AuthenticationException {

    MemberWalletTokenModel[] memberWalletTokenModelArr = null;
    // 월렛발급
    String responseModel = null;
    try {

      URI uri = makeUri(walletApi, ApiPathConstants.API_PATH_WALLET_TOKENS);
      responseModel = WebClientHelper.getInstance().get(uri);
      ;
      memberWalletTokenModelArr = objectMapper.readValue(responseModel,
          MemberWalletTokenModel[].class);

    } catch (Exception e) {
      throw new ServiceException(e.getMessage(), e.hashCode());
    }

    return MemberWalletTokensResponseModel.builder()
        .list(Stream.of(memberWalletTokenModelArr).collect(Collectors.toList()))
        .build();
  }


  public WalletBalanceResponseModel getBalance(WalletBalanceRequestModel requestModel)
      throws AuthenticationException {

    WalletBalanceResponseModel walletBalanceResponseModel = null;
    String responseModel = null;
    try {

      URI uri = makeUri(walletApi,
          "/users/" + requestModel.getUserId() + "/tokens/" + requestModel.getTokenId()
              + "/balance");
      responseModel = WebClientHelper.getInstance().get(uri);
      walletBalanceResponseModel = objectMapper.readValue(responseModel,
          WalletBalanceResponseModel.class);

    } catch (Exception e) {
      throw new ServiceException(e.getMessage(), e.hashCode());
    }

    return walletBalanceResponseModel;
  }

  public MemberWalletMyTokensResponseModel getMyTokens(String memberId)
      throws AuthenticationException {

    MemberWalletMyTokenModel[] MemberWalletMyTokenModelArr = null;
    // 월렛발급
    String responseModel = null;
    try {

      URI uri = makeUri(walletApi, "/users" + "/" + memberId + "/tokens");
      responseModel = WebClientHelper.getInstance().get(uri);
      MemberWalletMyTokenModelArr = objectMapper.readValue(responseModel,
          MemberWalletMyTokenModel[].class);


    } catch (Exception e) {
      throw new ServiceException(e.getMessage(), e.hashCode());
    }

    MemberWalletMyTokensResponseModel memberWalletMyTokensResponseModel = MemberWalletMyTokensResponseModel.builder()
        .list(Stream.of(MemberWalletMyTokenModelArr).collect(Collectors.toList()))
        .build();
    if (null != memberWalletMyTokensResponseModel.getList()) {
      memberWalletMyTokensResponseModel.getList().forEach(memberWalletMyTokenModel -> {
        if( memberWalletMyTokenModel.getBalance() < 0 ){
          memberWalletMyTokenModel.setBalance(0);
        }
        // 내부 지갑에 balance update
        memberWalletMapper.updateWalletBalance(MemberWalletDao.builder()
            .userId(memberWalletMyTokenModel.getUserId())
            .balance(new BigDecimal(memberWalletMyTokenModel.getBalance()).setScale(3,
                RoundingMode.DOWN))
            .build());
        int imageSize = 200;
        try {
          BitMatrix matrix = new MultiFormatWriter().encode(memberWalletMyTokenModel.getAddress(),
              BarcodeFormat.QR_CODE,
              imageSize, imageSize);
          ByteArrayOutputStream bos = new ByteArrayOutputStream();
          MatrixToImageWriter.writeToStream(matrix, "png", bos);
          String qrCode = Base64.getEncoder().encodeToString(bos.toByteArray());
          memberWalletMyTokenModel.setQrCode(qrCode);
        } catch (Exception e) {
          log.debug(e.getMessage());
        }
      });
    }

    return memberWalletMyTokensResponseModel;
  }

  @Transactional
  public WalletTransactionResponseModel postTransactionsPut(
      WalletTransactionPutRequestModel requestModel) throws AuthenticationException {

    WalletTransactionResponseModel walletTransactionResponseModel = null;

    String responseModel = null;
    if (requestModel.getAmount() < 0) {
      WalletBalanceRequestModel walletBalanceRequestModel = WalletBalanceRequestModel.builder()
          .memberId(requestModel.getMemberUid())
          .userId(requestModel.getUserId())
          .tokenId(2L)
          .build();
      WalletBalanceResponseModel walletBalanceResponseModel = this.getBalance(
          walletBalanceRequestModel);
      if (requestModel.getAmount() + walletBalanceResponseModel.getBalance() < 0) {
        throw new ServiceException(Constants.MSG_WALLET_INSUFFICIENT_BALANCE);
      }
    }

    URI uri = makeUri(walletApi, ApiPathConstants.API_PATH_WALLET_TOKENS + "/put");
    responseModel = WebClientHelper.getInstance().post(uri, requestModel, String.class);
    try {
      walletTransactionResponseModel = objectMapper.readValue(responseModel,
          WalletTransactionResponseModel.class);
      // 내부 지갑 balance update
      memberWalletMapper.updateWalletBalance(MemberWalletDao.builder()
          .userId(walletTransactionResponseModel.getUserId())
          .balance(BigDecimal.valueOf(walletTransactionResponseModel.getBalance()).setScale(3,
              RoundingMode.DOWN))
          .build());

      memberWalletMapper.insertWalletTransaction(
          WalletTransactionDao.builder()
              .transactionId(walletTransactionResponseModel.getTransactionId())
              .userId(walletTransactionResponseModel.getUserId())
              .tokenId(walletTransactionResponseModel.getTokenId())
              .status(walletTransactionResponseModel.getStatus())
              .amount(walletTransactionResponseModel.getAmount())
              .fee(walletTransactionResponseModel.getFee())
              .balance(walletTransactionResponseModel.getBalance())
              .txHash(walletTransactionResponseModel.getTxHash())
              .memo(walletTransactionResponseModel.getMemo())
              .toAddress(walletTransactionResponseModel.getToAddress())
              .toUserId(walletTransactionResponseModel.getToUserId())
              .regDate(walletTransactionResponseModel.getRegDate())
              .type(walletTransactionResponseModel.getType())
              .refTransactionId(walletTransactionResponseModel.getRefTransactionId())
              .fromAddress(walletTransactionResponseModel.getFromAddress())
              .feeSymbol(walletTransactionResponseModel.getFeeSymbol())
              .feeTokenId(walletTransactionResponseModel.getFeeTokenId())
              .priceKrw(walletTransactionResponseModel.getPriceKrw())
              .priceUsd(walletTransactionResponseModel.getPriceUsd())
              .innerTx(walletTransactionResponseModel.getInnerTx())
              .memberUid(requestModel.getMemberUid())
              .build());
    } catch (Exception e) {
      log.debug(e.getMessage());
    }

    return walletTransactionResponseModel;
  }

  @Transactional
  public WalletTransactionResponseModel postTransactionsWait(
      WalletTransactionWaitRequestModel requestModel) throws AuthenticationException {

    WalletTransactionResponseModel walletTransactionResponseModel = null;
    // 월렛발급
    String responseModel = null;
    try {

      URI uri = makeUri(walletApi, ApiPathConstants.API_PATH_WALLET_TOKENS + "/transactions/wait");
      responseModel = WebClientHelper.getInstance().post(uri, requestModel, String.class);
      walletTransactionResponseModel = objectMapper.readValue(responseModel,
          WalletTransactionResponseModel.class);
      memberWalletMapper.insertWalletTransaction(
          WalletTransactionDao.builder()
              .transactionId(walletTransactionResponseModel.getTransactionId())
              .userId(walletTransactionResponseModel.getUserId())
              .tokenId(walletTransactionResponseModel.getTokenId())
              .status(walletTransactionResponseModel.getStatus())
              .amount(walletTransactionResponseModel.getAmount())
              .fee(walletTransactionResponseModel.getFee())
              .balance(walletTransactionResponseModel.getBalance())
              .txHash(walletTransactionResponseModel.getTxHash())
              .memo(walletTransactionResponseModel.getMemo())
              .toAddress(walletTransactionResponseModel.getToAddress())
              .toUserId(walletTransactionResponseModel.getToUserId())
              .regDate(walletTransactionResponseModel.getRegDate())
              .type(walletTransactionResponseModel.getType())
              .refTransactionId(walletTransactionResponseModel.getRefTransactionId())
              .fromAddress(walletTransactionResponseModel.getFromAddress())
              .feeSymbol(walletTransactionResponseModel.getFeeSymbol())
              .feeTokenId(walletTransactionResponseModel.getFeeTokenId())
              .priceKrw(walletTransactionResponseModel.getPriceKrw())
              .priceUsd(walletTransactionResponseModel.getPriceUsd())
              .innerTx(walletTransactionResponseModel.getInnerTx())
              .memberUid(requestModel.getMemberUid())
              .build());
    } catch (Exception e) {
      throw new ServiceException(e.getMessage(), e.hashCode());
    }

    return walletTransactionResponseModel;
  }

  @Transactional
  public WalletTransactionResponseModel postTransactionsCancel(long transactionId)
      throws AuthenticationException {

    WalletTransactionResponseModel walletTransactionResponseModel = null;
    // 월렛발급
    String responseModel = null;
    try {
      // transaction 아이디 체크
      URI uri = makeUri(walletApi,
          ApiPathConstants.API_PATH_WALLET_TOKENS + "/transactions/" + transactionId + "/cancel");
      responseModel = WebClientHelper.getInstance().post(uri, new HashMap<>(), String.class);
      walletTransactionResponseModel = objectMapper.readValue(responseModel,
          WalletTransactionResponseModel.class);
      memberWalletMapper.insertWalletTransaction(
          WalletTransactionDao.builder()
              .transactionId(walletTransactionResponseModel.getTransactionId())
              .userId(walletTransactionResponseModel.getUserId())
              .tokenId(walletTransactionResponseModel.getTokenId())
              .status(walletTransactionResponseModel.getStatus())
              .amount(walletTransactionResponseModel.getAmount())
              .fee(walletTransactionResponseModel.getFee())
              .balance(walletTransactionResponseModel.getBalance())
              .txHash(walletTransactionResponseModel.getTxHash())
              .memo(walletTransactionResponseModel.getMemo())
              .toAddress(walletTransactionResponseModel.getToAddress())
              .toUserId(walletTransactionResponseModel.getToUserId())
              .regDate(walletTransactionResponseModel.getRegDate())
              .type(walletTransactionResponseModel.getType())
              .refTransactionId(walletTransactionResponseModel.getRefTransactionId())
              .fromAddress(walletTransactionResponseModel.getFromAddress())
              .feeSymbol(walletTransactionResponseModel.getFeeSymbol())
              .feeTokenId(walletTransactionResponseModel.getFeeTokenId())
              .priceKrw(walletTransactionResponseModel.getPriceKrw())
              .priceUsd(walletTransactionResponseModel.getPriceUsd())
              .innerTx(walletTransactionResponseModel.getInnerTx())
//          .memberUid(requestModel.getMemberUid())
              .build());
    } catch (Exception e) {
      throw new ServiceException(e.getMessage(), e.hashCode());
    }

    return walletTransactionResponseModel;
  }

  @Transactional
  public WalletTransactionResponseModel postTransactionsConfirm(long transactionId)
      throws AuthenticationException {

    WalletTransactionResponseModel walletTransactionResponseModel = null;
    // 월렛발급
    String responseModel = null;
    try {

      URI uri = makeUri(walletApi,
          ApiPathConstants.API_PATH_WALLET_TOKENS + "/transactions/confirm");
      responseModel = WebClientHelper.getInstance().post(uri, null, String.class);
      walletTransactionResponseModel = objectMapper.readValue(responseModel,
          WalletTransactionResponseModel.class);
      memberWalletMapper.insertWalletTransaction(
          WalletTransactionDao.builder()
              .transactionId(walletTransactionResponseModel.getTransactionId())
              .userId(walletTransactionResponseModel.getUserId())
              .tokenId(walletTransactionResponseModel.getTokenId())
              .status(walletTransactionResponseModel.getStatus())
              .amount(walletTransactionResponseModel.getAmount())
              .fee(walletTransactionResponseModel.getFee())
              .balance(walletTransactionResponseModel.getBalance())
              .txHash(walletTransactionResponseModel.getTxHash())
              .memo(walletTransactionResponseModel.getMemo())
              .toAddress(walletTransactionResponseModel.getToAddress())
              .toUserId(walletTransactionResponseModel.getToUserId())
              .regDate(walletTransactionResponseModel.getRegDate())
              .type(walletTransactionResponseModel.getType())
              .refTransactionId(walletTransactionResponseModel.getRefTransactionId())
              .fromAddress(walletTransactionResponseModel.getFromAddress())
              .feeSymbol(walletTransactionResponseModel.getFeeSymbol())
              .feeTokenId(walletTransactionResponseModel.getFeeTokenId())
              .priceKrw(walletTransactionResponseModel.getPriceKrw())
              .priceUsd(walletTransactionResponseModel.getPriceUsd())
              .innerTx(walletTransactionResponseModel.getInnerTx())
//          .memberUid(requestModel.getMemberUid())
              .build());
    } catch (Exception e) {
      throw new ServiceException(e.getMessage(), e.hashCode());
    }

    return walletTransactionResponseModel;
  }

  public WalletBalanceResponseModel getMasterBalance() throws AuthenticationException {

    WalletBalanceResponseModel walletBalanceResponseModel = null;
    // 월렛발급
    String responseModel = null;
    try {

      URI uri = makeUri(walletApi, "/tokens/2/master");
      responseModel = WebClientHelper.getInstance().get(uri);
      walletBalanceResponseModel = objectMapper.readValue(responseModel,
          WalletBalanceResponseModel.class);

    } catch (Exception e) {
      throw new ServiceException(e.getMessage(), e.hashCode());
    }

    return walletBalanceResponseModel;
  }

  public MemberWalletAdminTotalBalanceResponseModel getAdminMemberWalletTotalBalance() {
    MemberWalletAdminTotalBalanceDao dao = memberWalletMapper.getAdminTotalMemberWalletBalance();
    return convertMemberWalletAdminTotalBalanceResponseModel(dao);
  }

  private MemberWalletAdminTotalBalanceResponseModel convertMemberWalletAdminTotalBalanceResponseModel(
      MemberWalletAdminTotalBalanceDao dao) {
    return MemberWalletAdminTotalBalanceResponseModel.builder()
        .totalBalance(dao.getTotalBalance().setScale(3, RoundingMode.DOWN))
        .totalBalanceStr(dao.getTotalBalance().setScale(3, RoundingMode.DOWN).stripTrailingZeros()
            .toPlainString())
        .build();
  }

  private MemberWalletAdminResponseModel convertMemberWalletAdminResponseModel(
      MemberWalletAdminDao dao) {
    return MemberWalletAdminResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.isUsedFlag())
        .coinId(dao.getCoinId())
        .tokenId(dao.getTokenId())
        .name(dao.getName())
        .address(dao.getAddress())
        .accountId(dao.getAccountId())
        .memberUid(dao.getMemberUid())
        .mEth(dao.getMEth())
        .balance(dao.getBalance().setScale(3, RoundingMode.DOWN))
        .balanceStr(
            dao.getBalance().setScale(3, RoundingMode.DOWN).stripTrailingZeros().toPlainString())
        .userId(dao.getUserId())
        .referrerCode(dao.getReferrerCode())
        .referrer(dao.getReferrer())
        .migEth(dao.getMigEth())
        .migStaking(dao.getMigStaking())
        .email(dao.getEmail())
        .emailVerifiedFlag((dao.isEmailVerifiedFlag()))
        .nickname(dao.getNickname())
        .build();
  }

}
