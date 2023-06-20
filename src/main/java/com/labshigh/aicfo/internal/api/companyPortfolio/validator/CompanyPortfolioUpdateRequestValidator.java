package com.labshigh.aicfo.internal.api.companyPortfolio.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyFileInsertRequestModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyFinanceInsertRequestModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyPortfolioUpdateRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class CompanyPortfolioUpdateRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CompanyPortfolioUpdateRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CompanyPortfolioUpdateRequestModel requestModel = (CompanyPortfolioUpdateRequestModel) target;

    if (requestModel.getUid() < 1) {
      errors.reject("uid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uid"));
    }

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
