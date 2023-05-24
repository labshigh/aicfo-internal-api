package com.labshigh.aicfo.internal.api.withdraw.mapper;

import com.labshigh.aicfo.internal.api.withdraw.dao.WithdrawDao;
import com.labshigh.aicfo.internal.api.withdraw.model.request.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface WithdrawMapper {
    int count(WithdrawListRequestModel withdrawRequestModel);
    List<WithdrawDao> applyList(WithdrawListRequestModel withdrawRequestModel);
    int applyConfirmInsert(WithdrawConfirmInsertRequestModel requestModel);
    int applyAddConfirmRequest(WithdrawConfirmInsertRequestModel requestModel);
    List<WithdrawDao> rejectTypeList();
    int completeCount(WithdrawListRequestModel withdrawRequestModel);
    List<WithdrawDao> completeList(WithdrawListRequestModel withdrawRequestModel);
    int rejectCount(WithdrawListRequestModel withdrawRequestModel);
    List<WithdrawDao> rejectList(WithdrawListRequestModel withdrawRequestModel);
    long insertWithdrawalRequest(WithdrawInsertRequestModel withdrawInsertRequestModel);
    void updateWithdrawalRequestHistory(long uid);
    int selectMemberAddressCount(WithdrawInsertRequestModel withdrawInsertRequestModel );
    void updateWithdrawalStatus(WithdrawConfirmInsertRequestModel requestModel);
    String selectTxHash(WithdrawConfirmInsertRequestModel requestModel);
    int withdrawalHistoryCheck(WithdrawInsertRequestModel withdrawInsertRequestModel);
    WithdrawDao selectToMemberWallet(WithdrawInsertRequestModel withdrawInsertRequestModel);
    void updateTransactionId(long uid, long transactionId);

    WithdrawDao getToWalletInfo(WithdrawInsertRequestModel withdrawInsertRequestModel);

    WithdrawDao selectWalletTransactionInfo(long uid);
    WithdrawDao getWalletRequest(WithdrawDao withdrawDao);
    int applyConfirmCount(WithdrawConfirmInsertRequestModel requestModel);
    int applyAddConfirmCount(WithdrawConfirmInsertRequestModel requestModel);
    String getWithdrawalAddress(WithdrawInsertRequestModel withdrawInsertRequestModel);
}
