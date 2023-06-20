package com.labshigh.aicfo.internal.api.companyPortfolio.service;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.companyPortfolio.dao.CompanyFileDao;
import com.labshigh.aicfo.internal.api.companyPortfolio.dao.CompanyFinanceDao;
import com.labshigh.aicfo.internal.api.companyPortfolio.dao.CompanyPortfolioDao;
import com.labshigh.aicfo.internal.api.companyPortfolio.mapper.CompanyFileMapper;
import com.labshigh.aicfo.internal.api.companyPortfolio.mapper.CompanyFinanceMapper;
import com.labshigh.aicfo.internal.api.companyPortfolio.mapper.CompanyPortfolioMapper;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyFileInsertRequestModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyFinanceInsertRequestModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyPortfolioDeleteRequestModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyPortfolioDetailRequestModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyPortfolioInsertRequestModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyPortfolioUpdateRequestModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.response.CompanyFileResponseModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.response.CompanyFinanceResponseModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.response.CompanyPortfolioResponseModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class CompanyPortfolioService {

  @Value("${ncloud.object-storage.end-point}")
  private String s3EndPoint;
  @Value("${ncloud.object-storage.bucket}")
  private String s3NftBucket;

  @Autowired
  private CompanyPortfolioMapper companyPortfolioMapper;
  @Autowired
  private CompanyFinanceMapper companyFinanceMapper;
  @Autowired
  private CompanyFileMapper companyFileMapper;

  public CompanyPortfolioResponseModel detail(CompanyPortfolioDetailRequestModel requestModel) {

    CompanyPortfolioResponseModel result;

    CompanyPortfolioDao companyPortfolioDao = companyPortfolioMapper.detail(CompanyPortfolioDao
        .builder()
        .memberUid(requestModel.getMemberUid())
        .build());

    if (companyPortfolioDao == null) {
      return null;
    }

    result = convertCompanyPortfolioResponseModel(companyPortfolioDao, true);

    return result;
  }

  @Transactional
  public CompanyPortfolioResponseModel insert(CompanyPortfolioInsertRequestModel requestModel) {
    CompanyPortfolioResponseModel result;

    CompanyPortfolioDao portfolioDao = CompanyPortfolioDao.builder()
        .companyName(requestModel.getCompanyName())
        .phoneNumber(requestModel.getPhoneNumber())
        .category(requestModel.getCategory())
        .address(requestModel.getAddress())
        .ceoName(requestModel.getCeoName())
        .foundationAt(requestModel.getFoundationAt())
        .employeeCount(requestModel.getEmployeeCount())
        .content(requestModel.getContent())
        .publicFlag(requestModel.isPublicFlag())
        .memberUid(requestModel.getMemberUid())
        .build();

    companyPortfolioMapper.insert(portfolioDao);

    result = convertCompanyPortfolioResponseModel(portfolioDao, false);

    result.setFinanceList(
        requestModel.getFinanceList().isEmpty() ? Collections.emptyList() : new ArrayList<>());

    result.setFileList(
        requestModel.getFileList().isEmpty() ? Collections.emptyList() : new ArrayList<>());

    for (CompanyFinanceInsertRequestModel financeInsertRequestModel : requestModel.getFinanceList()) {
      CompanyFinanceDao financeDao = CompanyFinanceDao.builder()
          .year(financeInsertRequestModel.getYear())
          .capital(financeInsertRequestModel.getCapital())
          .sales(financeInsertRequestModel.getSales())
          .operatingProfit(financeInsertRequestModel.getOperatingProfit())
          .companyPortfolioUid(portfolioDao.getUid())
          .build();
      companyFinanceMapper.insert(financeDao);
      result.getFinanceList().add(convertCompanyFinanceResponseModel(financeDao));
    }

    for (CompanyFileInsertRequestModel fileInsertRequestModel : requestModel.getFileList()) {

      CompanyFileDao fileDao = CompanyFileDao.builder()
          .companyPortfolioUid(portfolioDao.getUid())
          .uri(fileInsertRequestModel.getUri())
          .fileName(fileInsertRequestModel.getFileName())
          .build();

      companyFileMapper.insert(fileDao);
      result.getFileList().add(convertCompanyFileResponseModel(fileDao));

    }

    return result;
  }

  @Transactional
  public CompanyPortfolioResponseModel update(
      CompanyPortfolioUpdateRequestModel requestModel) {
    CompanyPortfolioResponseModel result;

    CompanyPortfolioDao detail = companyPortfolioMapper.detail(
        CompanyPortfolioDao.builder().memberUid(
            requestModel.getMemberUid()).build());

    if (detail == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    CompanyPortfolioDao portfolioDao = CompanyPortfolioDao.builder()
        .companyName(requestModel.getCompanyName())
        .phoneNumber(requestModel.getPhoneNumber())
        .category(requestModel.getCategory())
        .address(requestModel.getAddress())
        .ceoName(requestModel.getCeoName())
        .foundationAt(requestModel.getFoundationAt())
        .employeeCount(requestModel.getEmployeeCount())
        .content(requestModel.getContent())
        .publicFlag(requestModel.isPublicFlag())
        .memberUid(requestModel.getMemberUid())
        .build();

    companyPortfolioMapper.update(portfolioDao);

    for (CompanyFinanceInsertRequestModel financeInsertRequestModel : requestModel.getFinanceList()) {
      CompanyFinanceDao financeDao = CompanyFinanceDao.builder()
          .year(financeInsertRequestModel.getYear())
          .capital(financeInsertRequestModel.getCapital())
          .sales(financeInsertRequestModel.getSales())
          .operatingProfit(financeInsertRequestModel.getOperatingProfit())
          .companyPortfolioUid(portfolioDao.getUid())
          .build();
      companyFinanceMapper.insert(financeDao);

    }

    for (CompanyFileInsertRequestModel fileInsertRequestModel : requestModel.getFileList()) {

      CompanyFileDao fileDao = CompanyFileDao.builder()
          .companyPortfolioUid(portfolioDao.getUid())
          .uri(fileInsertRequestModel.getUri())
          .fileName(fileInsertRequestModel.getFileName())
          .build();

      companyFileMapper.insert(fileDao);

    }

    if (requestModel.getDeleteFinanceUidList() != null) {
      for (Long financeUid : requestModel.getDeleteFinanceUidList()) {
        companyFinanceMapper.delete(CompanyFinanceDao.builder()
            .uid(financeUid)
            .companyPortfolioUid(requestModel.getUid()).build());
      }
    }

    if (requestModel.getDeleteFileUidList() != null) {
      for (Long fileUid : requestModel.getDeleteFileUidList()) {
        companyFinanceMapper.delete(CompanyFinanceDao.builder()
            .uid(fileUid)
            .companyPortfolioUid(requestModel.getUid()).build());
      }
    }

    result = convertCompanyPortfolioResponseModel(portfolioDao, true);

    return result;

  }

  @Transactional
  public void delete(CompanyPortfolioDeleteRequestModel requestModel) {
    CompanyPortfolioDao detail = companyPortfolioMapper.detail(
        CompanyPortfolioDao.builder().memberUid(
            requestModel.getMemberUid()).build());

    if (detail == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    companyFileMapper.deleteByPortfolioUid(CompanyFileDao.builder()
        .companyPortfolioUid(requestModel.getUid()).build());

    companyFinanceMapper.deleteByCompanyPortfolioUid(
        CompanyFinanceDao.builder().companyPortfolioUid(
            requestModel.getUid()).build());

    companyPortfolioMapper.detail(
        CompanyPortfolioDao.builder().uid(requestModel.getUid())
            .memberUid(requestModel.getMemberUid()).build());


  }


  private CompanyPortfolioResponseModel convertCompanyPortfolioResponseModel(
      CompanyPortfolioDao dao, boolean isSubBind) {

    List<CompanyFinanceResponseModel> financeList = null;
    List<CompanyFileResponseModel> fileList = null;

    if (isSubBind) {
      List<CompanyFinanceDao> companyFinanceDaoList = companyFinanceMapper.list(
          CompanyFinanceDao.builder()
              .companyPortfolioUid(dao.getUid())
              .build());

      financeList = companyFinanceDaoList.stream()
          .map(this::convertCompanyFinanceResponseModel).collect(Collectors.toList());

      List<CompanyFileDao> fileDaoList = companyFileMapper.list(CompanyFileDao.builder()
          .companyPortfolioUid(dao.getUid()).build());

      fileList = fileDaoList.stream()
          .map(this::convertCompanyFileResponseModel).collect(Collectors.toList());
    }

    return CompanyPortfolioResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .companyName(dao.getCompanyName())
        .phoneNumber(dao.getPhoneNumber())
        .category(dao.getCategory())
        .address(dao.getAddress())
        .ceoName(dao.getCeoName())
        .foundationAt(dao.getFoundationAt())
        .employeeCount(dao.getEmployeeCount())
        .content(dao.getContent())
        .publicFlag(dao.isPublicFlag())
        .memberUid(dao.getMemberUid())
        .financeList(financeList)
        .fileList(fileList)
        .build();
  }

  private CompanyFinanceResponseModel convertCompanyFinanceResponseModel(CompanyFinanceDao dao) {
    return CompanyFinanceResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .year(dao.getYear())
        .capital(dao.getCapital())
        .sales(dao.getSales())
        .operatingProfit(dao.getOperatingProfit())
        .build();
  }

  private CompanyFileResponseModel convertCompanyFileResponseModel(CompanyFileDao dao) {
    return CompanyFileResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .companyPortfolioUid(dao.getCompanyPortfolioUid())
        .uri(dao.getUri())
        .fileName(StringUtils.getFileUri(dao.getFileName(), s3EndPoint, s3NftBucket))
        .build();
  }

}
