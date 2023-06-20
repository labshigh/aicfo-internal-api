package com.labshigh.aicfo.internal.api.companyPortfolio.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyPortfolioDetailRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class CompanyPortfolioDetailRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CompanyPortfolioDetailRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CompanyPortfolioDetailRequestModel requestModel = (CompanyPortfolioDetailRequestModel) target;

    if (requestModel.getMemberUid() < 1) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

  }
}
