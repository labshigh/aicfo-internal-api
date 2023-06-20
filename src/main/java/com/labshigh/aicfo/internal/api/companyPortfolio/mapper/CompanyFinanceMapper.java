package com.labshigh.aicfo.internal.api.companyPortfolio.mapper;

import com.labshigh.aicfo.internal.api.companyPortfolio.dao.CompanyFinanceDao;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CompanyFinanceMapper {

  void insert(CompanyFinanceDao dao);

  void delete(CompanyFinanceDao dao);

  void deleteByCompanyPortfolioUid(CompanyFinanceDao dao);

  void update(CompanyFinanceDao dao);


  List<CompanyFinanceDao> list(CompanyFinanceDao dao);


}
