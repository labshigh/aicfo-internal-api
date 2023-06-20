package com.labshigh.aicfo.internal.api.companyPortfolio.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyFileInsertRequestModel {

  private long companyPortfolioUid;
  private String uri;
  private String fileName;
}
