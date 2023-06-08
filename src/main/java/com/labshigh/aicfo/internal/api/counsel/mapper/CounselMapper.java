package com.labshigh.aicfo.internal.api.counsel.mapper;

import com.labshigh.aicfo.internal.api.counsel.dao.CounselDao;
import com.labshigh.aicfo.internal.api.counsel.model.request.CounselListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CounselMapper {

  void insert(CounselDao dao);

  void updateComplete(CounselDao dao);

  int count(CounselListRequestModel requestModel);

  List<CounselDao> list(CounselListRequestModel requestModel);

  CounselDao detail(CounselDao dao);

}
