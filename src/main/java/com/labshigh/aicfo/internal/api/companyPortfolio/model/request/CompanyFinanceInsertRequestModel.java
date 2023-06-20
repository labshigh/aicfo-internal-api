package com.labshigh.aicfo.internal.api.companyPortfolio.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyFinanceInsertRequestModel {

  private String year;
  private String capital;
  private String sales;
  private String operatingProfit;
  private long companyPortfolioUid;

}
