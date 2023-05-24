package com.labshigh.aicfo.internal.api.log.mapper;

import com.labshigh.aicfo.internal.api.log.dao.LogItemBuyDao;
import com.labshigh.aicfo.internal.api.log.dao.LoggingDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LoggingMapper {

  void insert(LoggingDao dao);

  void update(LogItemBuyDao dao);

}
