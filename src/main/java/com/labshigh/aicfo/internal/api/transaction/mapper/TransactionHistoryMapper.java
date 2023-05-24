package com.labshigh.aicfo.internal.api.transaction.mapper;

import com.labshigh.aicfo.internal.api.transaction.dao.TransactionHistoryDao;
import com.labshigh.aicfo.internal.api.transaction.model.request.TransactionHistoryRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TransactionHistoryMapper {

  int count(TransactionHistoryRequestModel request);

  List<TransactionHistoryDao> list(TransactionHistoryRequestModel request);

  void insert(TransactionHistoryDao dao);

}
