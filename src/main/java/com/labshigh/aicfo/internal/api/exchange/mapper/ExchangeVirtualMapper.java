package com.labshigh.aicfo.internal.api.exchange.mapper;

import com.labshigh.aicfo.internal.api.exchange.dao.ExchangeVirtualDao;
import com.labshigh.aicfo.internal.api.exchange.model.request.ExchangeListGetRequestModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ExchangeVirtualMapper {

  ExchangeVirtualDao get(ExchangeVirtualDao exchangeVirtualDao);
  List<ExchangeVirtualDao> getList(ExchangeListGetRequestModel exchangeListGetRequestModel);

}
