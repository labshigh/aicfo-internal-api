package com.labshigh.aicfo.internal.api.inquiry.service;

import com.labshigh.aicfo.core.models.ResponseListModel;
import com.labshigh.aicfo.internal.api.inquiry.dao.InquiryDao;
import com.labshigh.aicfo.internal.api.inquiry.mapper.InquiryMapper;
import com.labshigh.aicfo.internal.api.inquiry.model.request.InquiryInsertRequestModel;
import com.labshigh.aicfo.internal.api.inquiry.model.request.InquiryListRequestModel;
import com.labshigh.aicfo.internal.api.inquiry.model.request.InquiryUpdateRequestModel;
import com.labshigh.aicfo.internal.api.inquiry.model.response.InquiryResponseModel;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class InquiryService {

  @Autowired
  private InquiryMapper inquiryMapper;

  public ResponseListModel listInquiry(InquiryListRequestModel requestModel) {
    ResponseListModel result = new ResponseListModel();

    int totalCount = inquiryMapper.count(requestModel);

    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;
    }

    List<InquiryResponseModel> list = inquiryMapper.list(requestModel).stream()
        .map(this::convertInquiryResponseModel).collect(
            Collectors.toList());

    result.setList(list);

    return result;
  }

  @Transactional
  public InquiryResponseModel insertInquiry(InquiryInsertRequestModel requestModel) {

    InquiryDao dao = InquiryDao.builder()
        .inquiryCommonCodeUid(requestModel.getInquiryCommonCodeUid())
        .inquiryTime(requestModel.getInquiryTime())
        .cfoUid(requestModel.getCfoUid())
        .memberUid(requestModel.getMemberUid())
        .build();

    inquiryMapper.insert(dao);
    return convertInquiryResponseModel(dao);

  }

  @Transactional
  public void updateInquiryTime(InquiryUpdateRequestModel requestModel) {
    inquiryMapper.updateInquiryTime(InquiryDao.builder()
        .uid(requestModel.getUid())
        .memberUid(requestModel.getMemberUid())
        .inquiryTime(requestModel.getInquiryTime())
        .build());
  }


  private InquiryResponseModel convertInquiryResponseModel(InquiryDao dao) {
    return InquiryResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .inquiryCommonCodeUid(dao.getInquiryCommonCodeUid())
        .inquiryCommonCodeName(dao.getInquiryCommonCodeName())
        .inquiryTime(dao.getInquiryTime())
        .cfoUid(dao.getCfoUid())
        .memberUid(dao.getMemberUid())
        .build();
  }

}
