package com.labshigh.aicfo.internal.api.admin.service;

import com.labshigh.aicfo.core.helper.CryptoHelper;
import com.labshigh.aicfo.core.models.ResponseListModel;
import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.admin.dao.AdminDAO;
import com.labshigh.aicfo.internal.api.admin.dao.AdminRoleDAO;
import com.labshigh.aicfo.internal.api.admin.mapper.AdminMapper;
import com.labshigh.aicfo.internal.api.admin.mapper.AdminRoleMapper;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminInsertRequestModel;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminListRequestModel;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminLoginHistoryModel;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminSigninRequestModel;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminUpdatePasswordRequestModel;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminUpdateRequestModel;
import com.labshigh.aicfo.internal.api.admin.model.response.AdminResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class AdminService {

  private final AdminMapper adminMapper;
  private final AdminRoleMapper adminRoleMapper;

  public AdminService(AdminMapper adminMapper, AdminRoleMapper adminRoleMapper) {
    this.adminMapper = adminMapper;
    this.adminRoleMapper = adminRoleMapper;
  }

  @Transactional
  public void insert(AdminInsertRequestModel adminInsertRequestModel) {

    String descryptPassword = CryptoHelper.decrypt(adminInsertRequestModel.getPassword());

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    String password = passwordEncoder.encode(descryptPassword);

    AdminDAO adminDAO = AdminDAO.builder()
        .adminId(adminInsertRequestModel.getAdminId())
        .password(password)
        .name(adminInsertRequestModel.getName())
        .nationalCode(adminInsertRequestModel.getNationalCode())
        .mobile(adminInsertRequestModel.getMobile())
        .usedFlag(adminInsertRequestModel.isUsedFlag())
        .build();

    adminMapper.insert(adminDAO);

    adminRoleMapper.insert(AdminRoleDAO.builder()
        .adminUid(adminDAO.getUid())
        .roleUid(adminInsertRequestModel.getRoleUid())
        .build());
  }

  @Transactional
  public void update(AdminUpdateRequestModel adminUpdateRequestModel) {

    if (adminMapper.check(adminUpdateRequestModel.getAdminUid()) == 0) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    String password = null;
    if (!StringUtils.isEmpty(adminUpdateRequestModel.getPassword())) {
      String descryptPassword = CryptoHelper.decrypt(adminUpdateRequestModel.getPassword());

      PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
      password = passwordEncoder.encode(descryptPassword);
    }

    AdminDAO adminDAO = AdminDAO.builder()
        .uid(adminUpdateRequestModel.getAdminUid())
        .name(adminUpdateRequestModel.getName())
        .password(password)
        .usedFlag(adminUpdateRequestModel.isUsedFlag())
        .nationalCode(adminUpdateRequestModel.getNationalCode())
        .mobile(adminUpdateRequestModel.getMobile())
        .build();

    adminMapper.update(adminDAO);

    adminRoleMapper.update(AdminRoleDAO.builder()
        .adminUid(adminUpdateRequestModel.getAdminUid())
        .roleUid(adminUpdateRequestModel.getRoleUid())
        .build());
  }

  @Transactional
  public void updatePassword(AdminUpdatePasswordRequestModel adminUpdatePasswordRequestModel) {

    AdminDAO adminDAO = adminMapper.detail(adminUpdatePasswordRequestModel.getAdminUid());

    if (adminDAO == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    String descryptCurrentPassword = CryptoHelper.decrypt(
        adminUpdatePasswordRequestModel.getCurrentPassword());

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    if (!passwordEncoder.matches(descryptCurrentPassword, adminDAO.getPassword())) {
      throw new ServiceException(String.format(Constants.MSG_WRONG_PASSWORD_ERROR));
    }

    String descryptPassword = CryptoHelper.decrypt(adminUpdatePasswordRequestModel.getPassword());

    String password = passwordEncoder.encode(descryptPassword);

    adminMapper.updatePassword(AdminDAO.builder()
        .uid(adminUpdatePasswordRequestModel.getAdminUid())
        .password(password)
        .build());
  }

  @Transactional
  public void updateLoginFailCount(AdminUpdateRequestModel requestModel) {

    if (adminMapper.check(requestModel.getAdminUid()) == 0) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    AdminDAO dao = AdminDAO.builder()
        .loginFailCount(requestModel.getLoginFailCount())
        .uid(requestModel.getAdminUid())
        .build();

    adminMapper.updateLoginFailCount(dao);
    if (requestModel.getLoginFailCount() == 3) {
      dao.setUsedFlag(false);
      adminMapper.updateUsedFlagByLoginFailCount(dao);
    }
  }

  @Transactional
  public void delete(long adminUid) {

    if (adminMapper.check(adminUid) == 0) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    adminMapper.delete(AdminDAO.builder()
        .deletedFlag(true)
        .uid(adminUid)
        .build());

    adminRoleMapper.deleteByAdminUid(AdminRoleDAO.builder()
        .deletedFlag(true)
        .adminUid(adminUid)
        .build());
  }

  public ResponseListModel list(AdminListRequestModel adminListRequestModel) {

    ResponseListModel responseListModel = new ResponseListModel();

    int totalCount = adminMapper.count(adminListRequestModel);

    if (totalCount <= 0) {
      responseListModel.setList(Collections.emptyList());
      responseListModel.setCurrentPage(adminListRequestModel.getPage());
      responseListModel.setTotalCount(totalCount);
      responseListModel.setPageSize(adminListRequestModel.getSize());
      return responseListModel;
    }

    List<AdminDAO> adminDAOList = adminMapper.list(adminListRequestModel);

    List<AdminResponseModel> adminResponseModelList = adminDAOList.stream()
        .map(adminDAO -> this._getByDao(adminDAO))
        .collect(Collectors.toList());

    responseListModel.setList(adminResponseModelList);
    responseListModel.setCurrentPage(adminListRequestModel.getPage());
    responseListModel.setTotalCount(totalCount);
    responseListModel.setPageSize(adminListRequestModel.getSize());

    return responseListModel;
  }

  public AdminResponseModel detail(long adminUid) {

    return this._getByDao(adminMapper.detail(adminUid));
  }

  public AdminResponseModel detailByAdminId(String adminId) {

    return this._getByDao(adminMapper.detailByAdminId(adminId));
  }

  public AdminResponseModel signin(AdminSigninRequestModel adminSigninRequestModel) {

    AdminDAO adminDAO = adminMapper.detailByAdminId(adminSigninRequestModel.getAdminId());

    if (adminDAO == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    String descryptPassword = CryptoHelper.decrypt(adminSigninRequestModel.getPassword());

//    String descryptPassword = memberLoginRequestModel.getPassword();

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    if (!passwordEncoder.matches(descryptPassword, adminDAO.getPassword())) {
      throw new ServiceException(String.format(Constants.MSG_WRONG_PASSWORD_ERROR));
    }

    return this._getByDao(adminDAO);
  }

  private AdminResponseModel _getByDao(AdminDAO adminDAO) {

    if (adminDAO == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    return AdminResponseModel.builder()
        .uid(adminDAO.getUid())
        .createdAt(adminDAO.getCreatedAt())
        .updatedAt(adminDAO.getUpdatedAt())
        .deletedFlag(adminDAO.isDeletedFlag())
        .usedFlag(adminDAO.isUsedFlag())
        .adminId(adminDAO.getAdminId())
        .name(adminDAO.getName())
        .password(adminDAO.getPassword())
        .mobile(adminDAO.getMobile())
        .nationalCode(adminDAO.getNationalCode())
        .roleUid(adminDAO.getRoleUid())
        .roleName(adminDAO.getRoleName())
        .loginFailCount(adminDAO.getLoginFailCount())
        .build();
  }

  @Transactional
  public void insertAdminLoginHistory(AdminLoginHistoryModel adminLoginHistoryModel) {

    try {
      adminMapper.insertAdminLoginHistory(adminLoginHistoryModel);
    } catch (ServiceException se) {
      throw new ServiceException(se.getMessage());
    }
  }
}
