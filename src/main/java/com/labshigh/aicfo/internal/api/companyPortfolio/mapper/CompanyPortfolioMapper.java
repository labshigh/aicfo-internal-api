package com.labshigh.aicfo.internal.api.companyPortfolio.mapper;

import com.labshigh.aicfo.internal.api.companyPortfolio.dao.CompanyPortfolioDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CompanyPortfolioMapper {

  void insert(CompanyPortfolioDao dao);

  void delete(CompanyPortfolioDao dao);

  void update(CompanyPortfolioDao dao);

  CompanyPortfolioDao detail(CompanyPortfolioDao dao);


}
