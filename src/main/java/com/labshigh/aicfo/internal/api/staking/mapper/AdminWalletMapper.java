package com.labshigh.aicfo.internal.api.staking.mapper;

import com.labshigh.aicfo.internal.api.staking.dao.AdminWalletDao;
import com.labshigh.aicfo.internal.api.staking.model.request.AdminWalletInsertRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AdminWalletMapper {

  List<AdminWalletDao> list(long adminUid);

  int count(AdminWalletInsertRequestModel requestModel);

  void insert(AdminWalletDao dao);

  void update(AdminWalletDao dao);

  void delete(AdminWalletDao dao);

}
