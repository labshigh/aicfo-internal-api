package com.labshigh.aicfo.internal.api.mainWallet.mapper;

import com.labshigh.aicfo.internal.api.mainWallet.dao.MainWalletDao;
import com.labshigh.aicfo.internal.api.mainWallet.model.request.MainWalletListRequestModel;
import com.labshigh.aicfo.internal.api.mainWallet.model.request.MainWalletRequestModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Component
public interface MainWalletMapper {
    int count(MainWalletListRequestModel model);
    List<MainWalletDao> list(MainWalletListRequestModel model);
    MainWalletDao withdrawalInfo(MainWalletRequestModel model);

    void mainWallerWithdrawalInsert(MainWalletDao dao);

    void updateTransactionId(long uid, long transactionId);

    BigDecimal pendingAmount();

}
