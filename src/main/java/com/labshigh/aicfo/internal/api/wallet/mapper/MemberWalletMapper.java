package com.labshigh.aicfo.internal.api.wallet.mapper;

import com.labshigh.aicfo.internal.api.member.model.request.MemberWithdrawalWalletSearchRequestModel;
import com.labshigh.aicfo.internal.api.wallet.dao.MemberWalletAdminDao;
import com.labshigh.aicfo.internal.api.wallet.dao.MemberWalletAdminTotalBalanceDao;
import com.labshigh.aicfo.internal.api.wallet.dao.MemberWalletDao;
import com.labshigh.aicfo.internal.api.wallet.dao.MemberWalletWithdrawalDao;
import com.labshigh.aicfo.internal.api.wallet.dao.WalletTransactionDao;
import com.labshigh.aicfo.internal.api.wallet.model.request.MemberWalletAdminRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.response.MemberWalletWithdrawalHistory;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MemberWalletMapper {

  void insertMemberWallet(MemberWalletDao memberWalletDao);

  void updateWalletAddress(MemberWalletDao memberWalletDao);

  void insertWithdrawalWallet(MemberWalletWithdrawalDao memberWalletDao);

  void insertWalletTransaction(WalletTransactionDao walletTransactionDao);

  void updateWalletBalance(MemberWalletDao memberWalletDao);

  void updateWalletMigEth(MemberWalletDao memberWalletDao);

  void updateWalletMigStaking(MemberWalletDao memberWalletDao);

  void updateMEth(MemberWalletDao memberWalletDao);

  void updateReferrer(MemberWalletDao memberWalletDao);

  void deleteWithdrawalWallet(MemberWalletWithdrawalDao memberWalletDao);

  MemberWalletDao get(MemberWalletDao memberWalletDao);

  List<MemberWalletDao> getList(MemberWalletDao memberWalletDao);

  MemberWalletWithdrawalDao getWithdrawalWallet(
      MemberWalletWithdrawalDao memberWalletWithdrawalDao);

  List<MemberWalletWithdrawalDao> getWithdrawalWalletList(
      MemberWithdrawalWalletSearchRequestModel requestModel);

  MemberWalletWithdrawalHistory withdrawalHistory(Long memberUid, String address);

  List<MemberWalletAdminDao> getAdminMemberWallet(MemberWalletAdminRequestModel requestModel);

  MemberWalletAdminTotalBalanceDao getAdminTotalMemberWalletBalance();

}
