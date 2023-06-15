package com.labshigh.aicfo.internal.api.inquiry.mapper;

import com.labshigh.aicfo.internal.api.inquiry.dao.InquiryDao;
import com.labshigh.aicfo.internal.api.inquiry.model.request.InquiryListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface InquiryMapper {

  void insert(InquiryDao dao);

  void updateInquiryTime(InquiryDao dao);

  int count(InquiryListRequestModel requestModel);

  List<InquiryDao> list(InquiryListRequestModel requestModel);

  InquiryDao detail(InquiryDao dao);
}
