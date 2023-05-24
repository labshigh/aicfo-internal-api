package com.labshigh.aicfo.internal.api.withdraw.service;

import com.labshigh.aicfo.core.helper.CryptoHelper;
import com.labshigh.aicfo.core.models.ResponseListModel;
import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.core.utils.JsonUtils;
import com.labshigh.aicfo.core.utils.RandomUtils;
import com.labshigh.aicfo.internal.api.admin.dao.AdminDAO;
import com.labshigh.aicfo.internal.api.admin.mapper.AdminMapper;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminSendVerifyEmailRequestModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.common.utils.MailUtils;
import com.labshigh.aicfo.internal.api.common.utils.TelegramUtils;
import com.labshigh.aicfo.internal.api.common.utils.models.MailReceiveInfo;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyInsertRequestModel;
import com.labshigh.aicfo.internal.api.member.dao.MemberDao;
import com.labshigh.aicfo.internal.api.member.mapper.MemberMapper;
import com.labshigh.aicfo.internal.api.member.model.request.MemberSendVerifyEmailRequestModel;
import com.labshigh.aicfo.internal.api.member.model.request.VerifyEmailModel;
import com.labshigh.aicfo.internal.api.transaction.dao.TransactionHistoryDao;
import com.labshigh.aicfo.internal.api.transaction.mapper.TransactionHistoryMapper;
import com.labshigh.aicfo.internal.api.wallet.dao.MemberWalletDao;
import com.labshigh.aicfo.internal.api.wallet.mapper.MemberWalletMapper;
import com.labshigh.aicfo.internal.api.wallet.model.request.WalletBalanceRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.request.WalletTransactionPutRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.response.WalletBalanceResponseModel;
import com.labshigh.aicfo.internal.api.wallet.service.MemberWalletService;
import com.labshigh.aicfo.internal.api.withdraw.dao.WithdrawDao;
import com.labshigh.aicfo.internal.api.withdraw.mapper.WithdrawMapper;
import com.labshigh.aicfo.internal.api.withdraw.model.request.WithdrawConfirmInsertRequestModel;
import com.labshigh.aicfo.internal.api.withdraw.model.request.WithdrawInsertRequestModel;
import com.labshigh.aicfo.internal.api.withdraw.model.request.WithdrawListRequestModel;
import com.labshigh.aicfo.internal.api.withdraw.model.request.WithdrawVerifyEmailRequestModel;
import com.labshigh.aicfo.internal.api.withdraw.model.response.WithdrawResponseModel;
import com.labshigh.aicfo.internal.api.withdraw.model.response.WithdrawVerifyEmailResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.naming.AuthenticationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class WithdrawService {
    private final WithdrawMapper withdrawMapper;
    private final MemberMapper memberMapper;
    private final AdminMapper adminMapper;
    private final MemberWalletService mws;
    private final TelegramUtils telegramUtils;

    private final TransactionHistoryMapper transactionHistoryMapper;
    private final MemberWalletMapper memberWalletMapper;
    @Value("${ncloud.mail-storage.tokenExpirationTime}")
    private long tokenExpirationTime;
    private final MailUtils mailUtils;

    private final SpringTemplateEngine thymeleafTemplateEngine;

    /**
     * 요청 목록
     * @param requestModel
     * @return
     */
    public ResponseListModel applyList(WithdrawListRequestModel requestModel) {
        ResponseListModel result = new ResponseListModel();

        try {
            int totalCount = withdrawMapper.count(requestModel);
            result.setCurrentPage(requestModel.getPage());
            result.setTotalCount(totalCount);
            result.setPageSize(requestModel.getSize());

            if (totalCount < 1) {
                return result;
            }

            List<WithdrawResponseModel> withdrawDaoList = withdrawMapper.applyList(requestModel).stream()
                    .map(this::convertWithdrawResponseModel).collect(Collectors.toList());
            result.setList(withdrawDaoList);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        return result;
    }

    public ResponseListModel rejectTypeList() {
        ResponseListModel result = new ResponseListModel();
        try {
            List<WithdrawResponseModel> withdrawDaoList = withdrawMapper.rejectTypeList().stream()
                    .map(this::convertWithdrawResponseModel).collect(Collectors.toList());
            result.setList(withdrawDaoList);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        return result;
    }

    /**
     * 승인 목록
     * @param requestModel
     * @return
     */
    public ResponseListModel completeList(WithdrawListRequestModel requestModel) {
        ResponseListModel result = new ResponseListModel();

        try {
            int totalCount = withdrawMapper.completeCount(requestModel);
            result.setCurrentPage(requestModel.getPage());
            result.setTotalCount(totalCount);
            result.setPageSize(requestModel.getSize());

            if (totalCount < 1) {
                return result;
            }

            List<WithdrawResponseModel> withdrawDaoList = withdrawMapper.completeList(requestModel).stream()
                    .map(this::convertWithdrawResponseModel).collect(Collectors.toList());
            result.setList(withdrawDaoList);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        return result;
    }

    /**
     * 거절 목록
     * @param requestModel
     * @return
     */
    public ResponseListModel rejectList(WithdrawListRequestModel requestModel) {
        ResponseListModel result = new ResponseListModel();

        try {
            int totalCount = withdrawMapper.rejectCount(requestModel);
            result.setCurrentPage(requestModel.getPage());
            result.setTotalCount(totalCount);
            result.setPageSize(requestModel.getSize());

            if (totalCount < 1) {
                return result;
            }

            List<WithdrawResponseModel> withdrawDaoList = withdrawMapper.rejectList(requestModel).stream()
                    .map(this::convertWithdrawResponseModel).collect(Collectors.toList());
            result.setList(withdrawDaoList);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        return result;
    }

    /**
     * 어드민 출금신청 1차 승인 및 거절 액션
     * @param requestModel
     * @return
     */
    @Transactional
    public ResponseModel applyConfirmInsert(WithdrawConfirmInsertRequestModel requestModel) throws AuthenticationException {
        ResponseModel result = new ResponseModel();
        int applyConfirmCount = withdrawMapper.applyConfirmCount(requestModel);
        if(applyConfirmCount >= 1) {
            result.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new ServiceException("이미 처리된 요청입니다.");
        }

        // withdraw_request_confirm 테이블에 데이터 등록
        withdrawMapper.applyConfirmInsert(requestModel);
        // 승인상태가 거절이거나 승인상태가 승인 이고 10 이더 이하인 경우 처리
        if(requestModel.isConfirmStatus() == false || (requestModel.isConfirmStatus() == true && requestModel.getRequestQuantity().compareTo(BigDecimal.TEN) < 0 ) ) {
            // 1차 거절 이거나 1차 승인 및 10이더 이하 인 경우는 1차에서 종료

            // withdraw_request(출금신청) 테이블의 status 변경
            withdrawMapper.updateWithdrawalStatus(requestModel);

            // Wallet 에 보낼 정보 세팅을 위한 조회
            WithdrawDao walletSendInfo = withdrawMapper.selectWalletTransactionInfo(requestModel.getWithdrawRequestUid());
            // 출금 거절
            if (requestModel.isConfirmStatus() == false) {
                // 사용자 출금 관리자 거절
                WalletTransactionPutRequestModel walletTransactionPutRequestModel =
                  WalletTransactionPutRequestModel.builder()
                    .userId(walletSendInfo.getUserId())
                    .tokenId(2L)
                    .coinId(2L)
                    .type("36")
                    .amount(walletSendInfo.getRequestQuantity().doubleValue())
                    .memo("사용자 출금 관리자 거절")
                    .event(false)
                    .memberUid(walletSendInfo.getMemberUid())
                    .build();
                mws.postTransactionsPut(walletTransactionPutRequestModel);

                // 지갑 활동 내역 추가
                TransactionHistoryDao transactionHistoryDao = TransactionHistoryDao.builder()
                  .price(walletSendInfo.getRequestQuantity())
                  .unit(ItemBuyInsertRequestModel.PriceUnit.ETH.name())
                  .memberUid(walletSendInfo.getMemberUid())
                  .transactionKindUid(36L)
                  .transactionUid(walletSendInfo.getUid())
                  .userId(walletSendInfo.getUserId())
                  .build();
                transactionHistoryMapper.insert(transactionHistoryDao);
            }
        }
        result.setData(requestModel.getUid());
        return result;
    }

    /**
     * 어드민 출금신청 2차 승인 및 거절 액션
     * @param requestModel
     * @return
     */
    @Transactional
    public ResponseModel applyAddConfirmRequest(WithdrawConfirmInsertRequestModel requestModel) throws AuthenticationException {
        ResponseModel result = new ResponseModel();
        int applyConfirmCount = withdrawMapper.applyAddConfirmCount(requestModel);
        if(applyConfirmCount >= 1) {
            result.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new ServiceException("이미 처리된 요청입니다.");
        }

        // withdraw_request_add_confirm 테이블에 데이터 등록
        withdrawMapper.applyAddConfirmRequest(requestModel);
        // withdraw_request(출금신청) 테이블의 status 변경
        withdrawMapper.updateWithdrawalStatus(requestModel);

        // Wallet 에 보낼 정보 세팅을 위한 조회
        WithdrawDao walletSendInfo = withdrawMapper.selectWalletTransactionInfo(requestModel.getWithdrawRequestUid());
        // 승인상태가 거절일 경우
        if(requestModel.isConfirmStatus() == false) {
            WalletTransactionPutRequestModel walletTransactionPutRequestModel =
              WalletTransactionPutRequestModel.builder()
                .userId(walletSendInfo.getUserId())
                .tokenId(2L)
                .coinId(2L)
                .type("36")
                .amount(walletSendInfo.getRequestQuantity().doubleValue())
                .memo("사용자 출금 관리자 거절")
                .event(false)
                .memberUid(walletSendInfo.getMemberUid())
                .build();
            mws.postTransactionsPut(walletTransactionPutRequestModel);

            // 지갑 활동 내역 추가
            TransactionHistoryDao transactionHistoryDao = TransactionHistoryDao.builder()
              .price(walletSendInfo.getRequestQuantity())
              .unit(ItemBuyInsertRequestModel.PriceUnit.ETH.name())
              .memberUid(walletSendInfo.getMemberUid())
              .transactionKindUid(36L)
              .transactionUid(walletSendInfo.getUid())
              .userId(walletSendInfo.getUserId())
              .build();
            transactionHistoryMapper.insert(transactionHistoryDao);
        }
        result.setData(requestModel.getUid());
        return result;
    }


    /**
     * 프론트 출금신청 API
     * @param withdrawInsertRequestModel
     * @return
     */
    @Transactional
    public ResponseModel insertWithdrawalRequest(WithdrawInsertRequestModel withdrawInsertRequestModel) {
        ResponseModel result = new ResponseModel();
        WithdrawInsertRequestModel insertModel = new WithdrawInsertRequestModel();
        insertModel.setMemberWalletWithdrawalUid(withdrawInsertRequestModel.getMemberWalletWithdrawalUid());
        insertModel.setMemberUid(withdrawInsertRequestModel.getMemberUid());
        insertModel.setWalletId(withdrawInsertRequestModel.getWalletId());

        // 내부 외부 구분 확인을 위한 지갑주소 조회
        String addr = withdrawMapper.getWithdrawalAddress(withdrawInsertRequestModel);

        int cnt = withdrawMapper.selectMemberAddressCount(insertModel);
        // 출금지갑 주소 등록되었는지 확인을 위한 부분
        if (cnt <= 0) {
            result.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new ServiceException("memberWalletWithdrawalUid 를 확인해주세요");
        } else {
            int checkCnt = withdrawMapper.withdrawalHistoryCheck(insertModel);
            // 기존 신청내역 중 승인 되었거나, 반려 후 이력삭제를 하지 않은 건이 있는지 체크
            if (checkCnt >= 1 ) {
                result.setStatus(HttpStatus.BAD_REQUEST.value());
                throw new ServiceException("완료되지 않은 신청내역이 있습니다.");
            } else {
                try {
                    WalletBalanceRequestModel walletBalanceRequestModel = WalletBalanceRequestModel.builder()
                      .memberId(withdrawInsertRequestModel.getMemberUid())
                      .userId(withdrawInsertRequestModel.getUserId())
                      .tokenId(2L)
                      .build();

                    // 잔액 조회
                    WalletBalanceResponseModel walletBalanceResponseModel=  mws.getBalance(walletBalanceRequestModel);
                    BigDecimal balance = BigDecimal.valueOf(walletBalanceResponseModel.getBalance()).setScale(3,RoundingMode.DOWN);
                    if( balance.compareTo(withdrawInsertRequestModel.getRequestQuantity()) < 0 ){
                        throw new ServiceException(Constants.MSG_WALLET_INSUFFICIENT_BALANCE);
                    }

                    // 주소를 통해 내부 외부 flag 처리
                    MemberWalletDao memberWalletDao = memberWalletMapper.get(MemberWalletDao.builder()
                            .address(addr)
                            .build());
                    // 객체가 있을 경우 내부 없을 경우 외부
                    if( null != memberWalletDao){
                        withdrawInsertRequestModel.setInternalFlag(true);
                    } else {
                        withdrawInsertRequestModel.setInternalFlag(false);
                    }
                    // 출금신청 등록
                    withdrawMapper.insertWithdrawalRequest(withdrawInsertRequestModel);

                    // 승인 전 미리 balance 차감
                    WalletTransactionPutRequestModel walletTransactionPutRequestModel =
                      WalletTransactionPutRequestModel.builder()
                        .userId(withdrawInsertRequestModel.getUserId())
                        .tokenId(2L)
                        .coinId(2L)
                        .type("35")
                        .amount(withdrawInsertRequestModel.getRequestQuantity().multiply(BigDecimal.valueOf(-1)).doubleValue())
                        .memo("사용자 출금 신청")
                        .event(false)
                        .memberUid(withdrawInsertRequestModel.getMemberUid())
                        .build();
                    mws.postTransactionsPut(walletTransactionPutRequestModel);

                    // 지갑 활동 내역 추가
                    TransactionHistoryDao transactionHistoryDao = TransactionHistoryDao.builder()
                      .price(withdrawInsertRequestModel.getRequestQuantity())
                      .unit(ItemBuyInsertRequestModel.PriceUnit.ETH.name())
                      .memberUid(withdrawInsertRequestModel.getMemberUid())
                      .transactionKindUid(35L)
                      .transactionUid(withdrawInsertRequestModel.getUid())
                      .userId(withdrawInsertRequestModel.getUserId())
                      .build();

                    transactionHistoryMapper.insert(transactionHistoryDao);

                    telegramUtils.postSendOnAdminTracking("Application for new financial settlement : " + withdrawInsertRequestModel.getUserId());

                    result.setStatus(HttpStatus.OK.value());
                } catch (Exception e) {
//                    result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    throw new ServiceException(e.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * 프론트 이력삭제 요청 API
     * @param uid
     * @return
     */
    @Transactional
    public ResponseModel updateWithdrawalRequestHistory(long uid) {
        ResponseModel result = new ResponseModel();
        try {
            withdrawMapper.updateWithdrawalRequestHistory(uid);
            result.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

        return result;
    }

    private WithdrawResponseModel convertWithdrawResponseModel(WithdrawDao dao) {
        return WithdrawResponseModel.builder()
                .uid(dao.getUid())
                .email(dao.getEmail())
                .createdAt(dao.getCreatedAt())
                .updatedAt(dao.getUpdatedAt())
                .memberUid(dao.getMemberUid())
                .fromWallet(dao.getFromWallet())
                .toWallet(dao.getToWallet())
                .txHash(dao.getTxHash())
                .requestQuantity(dao.getRequestQuantity())
                .addConfirmStatus(dao.isAddConfirmStatus())
                .confirmStatus(dao.isConfirmStatus())
                .rejectUid(dao.getRejectUid())
                .rejectMemo(dao.getRejectMemo())
                .addConfirmStatusCode(dao.getAddConfirmStatusCode())
                .confirmStatusCode(dao.getConfirmStatusCode())
                .rejectName(dao.getRejectName())
                .confirmDate(dao.getConfirmDate())
                .addConfirmDate(dao.getAddConfirmDate())
                .rejectDate(dao.getRejectDate())
                .rejectSequence(dao.getRejectSequence())
                .krwPrice(dao.getKrwPrice())
                .transactionId(dao.getTransactionId())
                .adminName(dao.getAdminName())
                .memberEmail(dao.getMemberEmail())
                .memberNickname(dao.getMemberNickname())
                .build();
    }

    /**
     * 텔레그램 메시지 발송
     * @param msg
     * @return
     */
    public ResponseModel sendTelegram(String msg) throws InterruptedException {
        return telegramUtils.postSend(msg);
    }

    public ResponseModel sendAdminVerifyEmail(AdminSendVerifyEmailRequestModel model, Constants.MailType mailType,
                                              HashMap<String, String> customMap) throws Exception{
        AdminDAO adminDAO = adminMapper.detail(model.getAdminUid());

        String code = RandomUtils.randomCodeNum(6);
        //토큰 생성
        VerifyEmailModel tokenModel = VerifyEmailModel.builder()
          .uid(adminDAO.getUid())
          .email(adminDAO.getAdminId())
          .code(code)
          .verifyTime(
            Timestamp.valueOf(LocalDateTime.now().plusMinutes(tokenExpirationTime)).getTime())
          .mailType(mailType)
          .build();

        String tokenJson = CryptoHelper.encrypt(JsonUtils.convertObjectToJsonString(tokenModel));
        MailReceiveInfo mailReceiveInfo = MailReceiveInfo.builder()
          .address(adminDAO.getAdminId())
          //.name(model.getName())
          .type("R")
          .parameters(customMap)
          .build();

        List<MailReceiveInfo> receiveInfoList = new ArrayList<>();
        receiveInfoList.add(mailReceiveInfo);
        ResponseModel result = null;
        Context thymeleafContext = new Context();
        Map<String, Object> templateModel = new HashMap<>();
        if(Constants.MailType.WITHDRAW_ADMIN.equals(mailType)) {

            templateModel.put("code", code);
            thymeleafContext.setVariables(templateModel);
            String contents = thymeleafTemplateEngine.process("mail-templates/withdraw", thymeleafContext);

            result = mailUtils.awsSESSend("Verification Code for Martini Pool External withdraw", contents, adminDAO.getAdminId());
            log.debug("mailSendResult : {}", JsonUtils.convertObjectToJsonString(result));
        }
        WithdrawVerifyEmailResponseModel responseModel = new WithdrawVerifyEmailResponseModel();
        responseModel.setToken(tokenJson);
        result.setData(responseModel);
        return result;
    }
    public ResponseModel sendVerifyEmail(MemberSendVerifyEmailRequestModel model, Constants.MailType mailType,
                                         HashMap<String, String> customMap) throws Exception{
        MemberDao memberDao = memberMapper.get(MemberDao.builder()
          .uid(model.getMemberUid())
          .build());
        String code = RandomUtils.randomCodeNum(6);
        //토큰 생성
        VerifyEmailModel tokenModel = VerifyEmailModel.builder()
          .uid(memberDao.getUid())
          .email(memberDao.getEmail())
          .code(code)
          .verifyTime(
            Timestamp.valueOf(LocalDateTime.now().plusMinutes(tokenExpirationTime)).getTime())
          .mailType(mailType)
          .build();

        String tokenJson = CryptoHelper.encrypt(JsonUtils.convertObjectToJsonString(tokenModel));
        MailReceiveInfo mailReceiveInfo = MailReceiveInfo.builder()
          .address(memberDao.getEmail())
          //.name(model.getName())
          .type("R")
          .parameters(customMap)
          .build();

        List<MailReceiveInfo> receiveInfoList = new ArrayList<>();
        receiveInfoList.add(mailReceiveInfo);
        ResponseModel result = null;
        Context thymeleafContext = new Context();
        Map<String, Object> templateModel = new HashMap<>();
        if(Constants.MailType.WITHDRAW.equals(mailType)) {

            templateModel.put("code", code);
            thymeleafContext.setVariables(templateModel);
            String contents = thymeleafTemplateEngine.process("mail-templates/withdraw", thymeleafContext);

            result = mailUtils.awsSESSend("Verification Code for Martini Pool External withdraw", contents, memberDao.getEmail());
            log.debug("mailSendResult : {}", JsonUtils.convertObjectToJsonString(result));
        }
        WithdrawVerifyEmailResponseModel responseModel = new WithdrawVerifyEmailResponseModel();
        responseModel.setToken(tokenJson);
        result.setData(responseModel);
        return result;
    }

    @Transactional
    public void verifyEmailCode(WithdrawVerifyEmailRequestModel requestModel) {

       this.checkToken(requestModel, Constants.MailType.WITHDRAW);

    }

    @Transactional
    public void verifyAdminEmailCode(WithdrawVerifyEmailRequestModel requestModel) {
        this.checkToken(requestModel, Constants.MailType.WITHDRAW_ADMIN);
    }



    public void checkToken(WithdrawVerifyEmailRequestModel requestModel, Constants.MailType mailType) {
        VerifyEmailModel verifyEmailModel = JsonUtils.convertJsonStringToObject(
          CryptoHelper.decrypt(requestModel.getToken()), VerifyEmailModel.class);
        log.debug("verifyEmailModel : {}", JsonUtils.convertObjectToJsonString(verifyEmailModel));

        if (verifyEmailModel == null || !verifyEmailModel.getMailType().equals(mailType)) {
            throw new ServiceException(Constants.MSG_CODE_ERROR);
        }

        if (!verifyEmailModel.getCode().equals(requestModel.getCode())) {
            throw new ServiceException(Constants.MSG_CODE_ERROR);
        }


        long curTimeStamp = new Date().getTime();

        log.debug("curTimeStamp : {}", curTimeStamp);
        if (curTimeStamp > verifyEmailModel.getVerifyTime()) {
            throw new ServiceException(Constants.MSG_CODE_ERROR);
        }

    }
}
