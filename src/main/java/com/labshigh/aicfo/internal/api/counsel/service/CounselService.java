package com.labshigh.aicfo.internal.api.counsel.service;

import com.labshigh.aicfo.core.models.ResponseListModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.counsel.dao.CounselDao;
import com.labshigh.aicfo.internal.api.counsel.dao.CounselFileDao;
import com.labshigh.aicfo.internal.api.counsel.mapper.CounselFileMapper;
import com.labshigh.aicfo.internal.api.counsel.mapper.CounselMapper;
import com.labshigh.aicfo.internal.api.counsel.model.request.CounselDetailRequestModel;
import com.labshigh.aicfo.internal.api.counsel.model.request.CounselFileInsertModel;
import com.labshigh.aicfo.internal.api.counsel.model.request.CounselInsertRequestModel;
import com.labshigh.aicfo.internal.api.counsel.model.request.CounselListRequestModel;
import com.labshigh.aicfo.internal.api.counsel.model.request.CounselUpdateRequestModel;
import com.labshigh.aicfo.internal.api.counsel.model.response.CounselFileResponseModel;
import com.labshigh.aicfo.internal.api.counsel.model.response.CounselResponseModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class CounselService {

  @Value("${ncloud.object-storage.end-point}")
  private String s3EndPoint;
  @Value("${ncloud.object-storage.bucket}")
  private String s3NftBucket;

  @Autowired
  private CounselMapper counselMapper;

  @Autowired
  private CounselFileMapper counselFileMapper;

  public ResponseListModel listCounsel(CounselListRequestModel requestModel) {

    ResponseListModel result = new ResponseListModel();

    int totalCount = counselMapper.count(requestModel);

    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;
    }

    List<CounselResponseModel> list = counselMapper.list(requestModel).stream()
        .map(v -> convertCounselResponseModel(v, true)).collect(Collectors.toList());

    result.setList(list);

    return result;
  }

  public CounselResponseModel detailCounsel(CounselDetailRequestModel requestModel) {
    CounselDao dao = counselMapper.detail(CounselDao.builder().uid(requestModel.getCounselUid())
        .memberUid(requestModel.getMemberUid()).build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    return convertCounselResponseModel(dao, true);

  }

  @Transactional
  public CounselResponseModel insertCounsel(CounselInsertRequestModel requestModel) {

    CounselResponseModel result;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    LocalDateTime counselAt = LocalDateTime.parse(requestModel.getCounselAt(), formatter);

    CounselDao dao = CounselDao.builder()
        .counselKindCommonCodeUid(requestModel.getCounselKindCommonCodeUid())
        .counselReservationCommonCodeUid(requestModel.getCounselReservationCommonCodeUid())
        .cfoUid(requestModel.getCfoUid())
        .memberUid(requestModel.getMemberUid())
        .counselPaymentCommonCodeUid(requestModel.getCounselPaymentCommonCodeUid())
        //.counselAt(requestModel.getCounselAt())
        .counselAt(counselAt)
        .content(requestModel.getContent())
        .phoneNumber(requestModel.getPhoneNumber())
        .phoneVerifiedFlag(requestModel.isPhoneVerifiedFlag())
        .counselAtCommonCodeUid(requestModel.getCounselAtCommonCodeUid())
        .build();

    counselMapper.insert(dao);

    result = convertCounselResponseModel(dao, false);
    result.setFileList(
        requestModel.getFileList().isEmpty() ? Collections.emptyList() : new ArrayList<>());

    if (!requestModel.getFileList().isEmpty()) {
      for (CounselFileInsertModel counselFileInsertModel : requestModel.getFileList()) {
        CounselFileDao counselFileDao = CounselFileDao.builder()
            .counselUid(dao.getUid())
            .uri(getReplaceFileUri(counselFileInsertModel.getUri()))
            .fileName(counselFileInsertModel.getFileName())
            .build();

        counselFileMapper.insert(counselFileDao);

        result.getFileList().add(convertCounselFileResponse(counselFileDao));
      }

    }

    return result;
  }

  @Transactional
  public void updateCompleteCounsel(CounselUpdateRequestModel requestModel) {
    CounselDao dao = CounselDao.builder()
        .uid(requestModel.getCounselUid())
        .memberUid(requestModel.getMemberUid())
        .build();

    CounselDao detail = counselMapper.detail(dao);

    if (detail.isCancelFlag()) {
      throw new ServiceException(Constants.MSG_COUNSEL_ALREADY_CANCEL_ERROR);
    }

    if (detail.isCompleteFlag()) {
      throw new ServiceException(Constants.MSG_COUNSEL_ALREADY_COMPLETE_ERROR);
    }

    counselMapper.updateComplete(dao);
  }

  @Transactional
  public void updateCancelCounsel(CounselUpdateRequestModel requestModel) {
    CounselDao dao = CounselDao.builder()
        .uid(requestModel.getCounselUid())
        .memberUid(requestModel.getMemberUid())
        .cancelReasonCommonCodeUid(requestModel.getCancelReasonCommonCodeUid())
        .cancelReason(requestModel.getCancelReason())
        .build();

    CounselDao detail = counselMapper.detail(dao);

    if (detail.getCounselAt().isBefore(LocalDateTime.now())) {
      throw new ServiceException(Constants.MSG_COUNSEL_NOW_DATETIME_CANCEL_ERROR);
    }

    if (detail.isCompleteFlag()) {
      throw new ServiceException(Constants.MSG_COUNSEL_ALREADY_COMPLETE_ERROR);
    }

    if (detail.isCancelFlag()) {
      throw new ServiceException(Constants.MSG_COUNSEL_ALREADY_CANCEL_ERROR);
    }

    counselMapper.updateCancel(dao);
  }


  private CounselResponseModel convertCounselResponseModel(CounselDao dao, boolean isFileListBind) {

    List<CounselFileResponseModel> fileList = null;
    if (isFileListBind) {
      List<CounselFileDao> fileDaoList = counselFileMapper.list(
          CounselFileDao.builder().counselUid(dao.getUid()).build());

      fileList = fileDaoList.stream()
          .map(this::convertCounselFileResponse).collect(Collectors.toList());
    }
    return CounselResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .counselKindCommonCodeUid(dao.getCounselKindCommonCodeUid())
        .counselKindCommonCodeName(dao.getCounselKindCommonCodeName())
        .counselReservationCommonCodeUid(dao.getCounselReservationCommonCodeUid())
        .counselReservationCommonCodeName(dao.getCounselReservationCommonCodeName())
        .cfoUid(dao.getCfoUid())
        .memberUid(dao.getMemberUid())
        .counselPaymentCommonCodeUid(dao.getCounselPaymentCommonCodeUid())
        .counselAt(dao.getCounselAt())
        .content(dao.getContent())
        .phoneNumber(dao.getPhoneNumber())
        .phoneVerifiedFlag(dao.isPhoneVerifiedFlag())
        .counselAtCommonCodeUid(dao.getCounselAtCommonCodeUid())
        .counselAtCommonCodeName(dao.getCounselAtCommonCodeName())
        .cfoName(dao.getCfoName())
        .career(dao.getCareer())
        .profileUri(dao.getProfileUri())
        .cancelFlag(dao.isCancelFlag())
        .cancelReasonCommonCodeUid(dao.getCancelReasonCommonCodeUid())
        .cancelReasonCommonCodeName(dao.getCancelReasonCommonCodeName())
        .cancelReason(dao.getCancelReason())
        .cancelAt(dao.getCancelAt())
        .fileList(fileList)
        .build();

  }

  private CounselFileResponseModel convertCounselFileResponse(CounselFileDao dao) {
    return CounselFileResponseModel.builder()
        .uid(dao.getUid())
        .usedFlag(dao.getUsedFlag())
        .deletedFlag(dao.isDeletedFlag())
        .fileName(dao.getFileName())
        .uri(getFileUri(dao.getUri()))
        .counselUid(dao.getCounselUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .build();
  }

  private String getFileUri(String uri) {

    if (uri == null) {
      return null;
    }

    return "https://" + s3EndPoint + "/" + s3NftBucket + "/" + uri;
  }


  private String getReplaceFileUri(String uri) {

    if (uri == null) {
      return null;
    }

    return uri.replaceAll("https://" + s3EndPoint + "/" + s3NftBucket + "/", "");
  }


}
