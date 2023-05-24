package com.labshigh.aicfo.internal.api.log.service;

import com.labshigh.aicfo.internal.api.log.dao.LoggingDao;
import com.labshigh.aicfo.internal.api.log.mapper.LogItemBuyMapper;
import com.labshigh.aicfo.internal.api.log.mapper.LoggingMapper;
import com.labshigh.aicfo.internal.api.log.model.request.LoggingInsertRequestModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class LoggingService {

  private final LoggingMapper loggingMapper;
  private final LogItemBuyMapper logItemBuyMapper;

  @Transactional
  public void insertLogging(LoggingInsertRequestModel requestModel) {
    LoggingDao loggingDao = LoggingDao.builder()
      .ip(requestModel.getIp())
      .accessType(requestModel.getAccessType())
      .httpMethodType(requestModel.getHttpMethodType())
      .apiUrl(requestModel.getApiUrl())
      .apiParameters(requestModel.getApiParameters())
      .email(requestModel.getEmail())
      .build();

    loggingMapper.insert(loggingDao);
  }

}
