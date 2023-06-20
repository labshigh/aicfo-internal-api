package com.labshigh.aicfo.internal.api.companyPortfolio.model.request;

import java.sql.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyPortfolioUpdateRequestModel {

  private long uid;
  private String companyName;
  private String phoneNumber;
  private String category;
  private String address;
  private String ceoName;
  private Date foundationAt;
  private Integer employeeCount;
  private String content;
  private boolean publicFlag;
  private long memberUid;

  private List<CompanyFinanceInsertRequestModel> financeList;
  private List<CompanyFileInsertRequestModel> fileList;
  private List<Long> deleteFinanceUidList;
  private List<Long> deleteFileUidList;

}
