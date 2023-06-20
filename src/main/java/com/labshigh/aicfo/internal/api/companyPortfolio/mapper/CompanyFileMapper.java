package com.labshigh.aicfo.internal.api.companyPortfolio.mapper;

import com.labshigh.aicfo.internal.api.companyPortfolio.dao.CompanyFileDao;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CompanyFileMapper {

  void insert(CompanyFileDao dao);

  void delete(CompanyFileDao dao);

  void deleteByPortfolioUid(CompanyFileDao dao);

  List<CompanyFileDao> list(CompanyFileDao dao);


}
