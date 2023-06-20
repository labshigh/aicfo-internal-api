package com.labshigh.aicfo.internal.api.companyPortfolio.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyPortfolioDeleteRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class CompanyPortfolioDeleteRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CompanyPortfolioDeleteRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CompanyPortfolioDeleteRequestModel requestModel = (CompanyPortfolioDeleteRequestModel) target;

    if (requestModel.getUid() < 1) {
      errors.reject("uid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uid"));
    }

    if (requestModel.getMemberUid() < 1) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

  }
}
