package com.labshigh.aicfo.internal.api.companyPortfolio.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanyPortfolioResponseModel {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private Boolean usedFlag;

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

  List<CompanyFinanceResponseModel> financeList;
  List<CompanyFileResponseModel> fileList;

}
