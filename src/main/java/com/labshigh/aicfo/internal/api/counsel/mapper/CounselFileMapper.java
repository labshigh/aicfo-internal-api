package com.labshigh.aicfo.internal.api.counsel.mapper;

import com.labshigh.aicfo.internal.api.counsel.dao.CounselFileDao;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CounselFileMapper {

  void insert(CounselFileDao dao);

  void delete(CounselFileDao dao);

  List<CounselFileDao> list(CounselFileDao dao);


}
