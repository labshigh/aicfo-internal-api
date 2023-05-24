package com.labshigh.aicfo.internal.api.exchange.mapper;

import com.labshigh.aicfo.internal.api.exchange.dao.ExchangeDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ExchangeMapper {

  ExchangeDao get(ExchangeDao exchangeDao);

}
