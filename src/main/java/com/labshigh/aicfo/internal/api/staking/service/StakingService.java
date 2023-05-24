package com.labshigh.aicfo.internal.api.staking.service;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.staking.dao.AdminWalletDao;
import com.labshigh.aicfo.internal.api.staking.mapper.AdminWalletMapper;
import com.labshigh.aicfo.internal.api.staking.model.request.AdminWalletInsertRequestModel;
import com.labshigh.aicfo.internal.api.staking.model.request.AdminWalletUpdateRequestModel;
import com.labshigh.aicfo.internal.api.staking.model.response.AdminWalletResponseModel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class StakingService {

  @Autowired
  private AdminWalletMapper adminWalletMapper;

  public List<AdminWalletResponseModel> listAdminWallet(long adminUid) {

    List<AdminWalletDao> daoList = adminWalletMapper.list(adminUid);

    return daoList.stream().map(this::convertAdminWalletResponseModel).collect(Collectors.toList());
  }

  @Transactional
  public void insertAdminWallet(AdminWalletInsertRequestModel requestModel) {

    int count = adminWalletMapper.count(requestModel);

    if (count > 0) {
      throw new ServiceException(Constants.MSG_DUPLICATED_DATA);
    }

    adminWalletMapper.insert(AdminWalletDao.builder().walletAddress(requestModel.getWalletAddress())
        .description(requestModel.getDescription())
        .adminUid(requestModel.getAdminUid())
        .build());
  }

  @Transactional
  public void updateAdminWallet(AdminWalletUpdateRequestModel requestModel) {
    adminWalletMapper.update(AdminWalletDao.builder()
        .description(requestModel.getDescription())
        .uid(requestModel.getUid())
        .adminUid(requestModel.getAdminUid())
        .build());
  }

  @Transactional
  public void deleteAdminWallet(AdminWalletUpdateRequestModel requestModel) {
    adminWalletMapper.delete(AdminWalletDao.builder()
        .description(requestModel.getDescription())
        .uid(requestModel.getUid())
        .adminUid(requestModel.getAdminUid())
        .build());
  }

  private AdminWalletResponseModel convertAdminWalletResponseModel(AdminWalletDao dao) {
    return AdminWalletResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .walletAddress(dao.getWalletAddress())
        .description(dao.getDescription())
        .adminUid(dao.getAdminUid())
        .build();
  }

}
