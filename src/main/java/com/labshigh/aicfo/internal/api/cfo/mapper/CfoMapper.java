package com.labshigh.aicfo.internal.api.cfo.mapper;

import com.labshigh.aicfo.internal.api.cfo.dao.CfoDao;
import com.labshigh.aicfo.internal.api.cfo.model.request.CfoListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CfoMapper {

  void insert(CfoDao dao);

  List<CfoDao> list(CfoListRequestModel requestModel);

  CfoDao delete(CfoDao dao);

}
