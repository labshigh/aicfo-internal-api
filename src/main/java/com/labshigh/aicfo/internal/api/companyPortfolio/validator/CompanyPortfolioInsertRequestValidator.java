package com.labshigh.aicfo.internal.api.companyPortfolio.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyFileInsertRequestModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyFinanceInsertRequestModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyPortfolioInsertRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class CompanyPortfolioInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CompanyPortfolioInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CompanyPortfolioInsertRequestModel requestModel = (CompanyPortfolioInsertRequestModel) target;

    if (requestModel.getMemberUid() < 1) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

    if (StringUtils.isEmpty(requestModel.getCompanyName())) {
      errors.reject("companyName.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "companyName"));
    }

    for (CompanyFinanceInsertRequestModel financeInsertRequestModel : requestModel.getFinanceList()) {
      if (StringUtils.isEmpty(financeInsertRequestModel.getYear())) {
        errors.reject("year.required",
            String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "year"));
      }
    }

    for (CompanyFileInsertRequestModel fileInsertRequestModel : requestModel.getFileList()) {
      if (StringUtils.isEmpty(fileInsertRequestModel.getUri())) {
        errors.reject("uri.required",
            String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uri"));
      }
      if (StringUtils.isEmpty(fileInsertRequestModel.getFileName())) {
        errors.reject("fileName.required",
            String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "fileName"));
      }
    }


  }
}
