package com.labshigh.aicfo.internal.api.transaction.mapper;

import com.labshigh.aicfo.internal.api.transaction.dao.TransactionEventDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TransactionEventMapper {

  void insert(TransactionEventDao dao);

}
