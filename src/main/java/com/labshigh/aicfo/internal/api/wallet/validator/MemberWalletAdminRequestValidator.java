package com.labshigh.aicfo.internal.api.wallet.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.wallet.model.request.MemberWalletAdminRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberWalletAdminRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberWalletAdminRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {

    MemberWalletAdminRequestModel requestModel = (MemberWalletAdminRequestModel) target;

    if (StringUtils.isEmpty(requestModel.getSearch())) {
      errors.reject("search.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "search"));
    }
    if (requestModel.getSearchType() == null) {
      errors.reject("searchType.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "searchType"));
    }

  }
}
