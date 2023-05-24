package com.labshigh.aicfo.internal.api.mainWallet.service;

import com.labshigh.aicfo.core.models.ResponseListModel;
import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.common.utils.TelegramUtils;
import com.labshigh.aicfo.internal.api.mainWallet.dao.MainWalletDao;
import com.labshigh.aicfo.internal.api.mainWallet.mapper.MainWalletMapper;
import com.labshigh.aicfo.internal.api.mainWallet.model.request.MainWalletListRequestModel;
import com.labshigh.aicfo.internal.api.mainWallet.model.request.MainWalletRequestModel;
import com.labshigh.aicfo.internal.api.mainWallet.model.response.MainWalletResponseModel;
import com.labshigh.aicfo.internal.api.transaction.mapper.TransactionHistoryMapper;
import com.labshigh.aicfo.internal.api.wallet.dao.MemberWalletDao;
import com.labshigh.aicfo.internal.api.wallet.mapper.MemberWalletMapper;
import com.labshigh.aicfo.internal.api.wallet.service.MemberWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MainWalletService {
    private final MainWalletMapper mainWalletMapper;
    private final MemberWalletService mws;
    private final MemberWalletMapper memberWalletMapper;
    private final TransactionHistoryMapper transactionHistoryMapper;
    private final TelegramUtils telegramUtils;

    public ResponseListModel list(MainWalletListRequestModel model) {
        ResponseListModel result = new ResponseListModel();
        try {
            int totalCount = mainWalletMapper.count(model);
            result.setCurrentPage(model.getPage());
            result.setTotalCount(totalCount);
            result.setPageSize(model.getSize());

            if (totalCount < 1) {
                return result;
            }

            List<MainWalletResponseModel> mainWalletDaoList = mainWalletMapper.list(model).stream()
                            .map(this::convertMainWalletListResponseModel).collect(Collectors.toList());
            result.setList(mainWalletDaoList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }

        return result;
    }

    @Transactional
    public ResponseModel request(MainWalletRequestModel model) {
        ResponseModel result = new ResponseModel();
        try {
//            MainWalletDao dao = mainWalletMapper.withdrawalInfo(model);
            MainWalletDao dao = new MainWalletDao();
            dao = new MainWalletDao();
            dao.setWalletAddress(model.getWalletAddress());
            dao.setAmount(model.getAmount());

            dao.setFromAddress(mws.getMasterBalance().getAddress());
            dao.setAmount(model.getAmount());
            dao.setAdminUid(model.getAdminUid());

            MemberWalletDao memberWalletDao = memberWalletMapper.get(MemberWalletDao.builder()
                    .address(model.getWalletAddress())
                    .build());

            LocalDateTime now = LocalDateTime.now();
            String formatNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            boolean internalWalletFlag = false;
            String msg = "메인지갑에서 외부지갑: "+model.getWalletAddress()+"  ["+model.getAmount()+"] ETH / ["+formatNow+"] 출금 요청";
            if( null != memberWalletDao){
                internalWalletFlag = true;
                msg = "메인지갑에서 "+model.getWalletAddress()+"["+model.getAmount()+"] ETH / ["+formatNow+"] 출금 요청";
            }
            telegramUtils.postSendOnMainWallet(msg);

            dao.setInternalFlag(internalWalletFlag);
            // insert Mapper 작성
            mainWalletMapper.mainWallerWithdrawalInsert(dao);

            return result;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private MainWalletResponseModel convertMainWalletListResponseModel(MainWalletDao dao) {
        return MainWalletResponseModel.builder()
                .uid(dao.getUid())
                .walletAddress(dao.getWalletAddress())
                .amount(dao.getAmount())
                .gasAmount(dao.getGasAmount())
                .totalAmount(dao.getTotalAmount())
                .updateAt(dao.getUpdateAt())
                .withdrawalStatus(dao.getWithdrawalStatus())
                .adminName(dao.getAdminName())
                .createdAt(dao.getCreatedAt())
                .build();
    }

    public BigDecimal pendingAmount() {
        try {
            return mainWalletMapper.pendingAmount();
        }  catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
