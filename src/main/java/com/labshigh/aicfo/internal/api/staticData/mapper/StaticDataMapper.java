package com.labshigh.aicfo.internal.api.staticData.mapper;

import com.labshigh.aicfo.internal.api.staticData.dao.StaticDataDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface StaticDataMapper {

  StaticDataDao detail(long staticDataUid);
}