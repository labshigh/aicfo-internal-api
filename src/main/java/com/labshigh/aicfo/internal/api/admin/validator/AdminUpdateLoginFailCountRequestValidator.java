package com.labshigh.aicfo.internal.api.admin.validator;

import com.labshigh.aicfo.internal.api.admin.model.request.AdminUpdateRequestModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class AdminUpdateLoginFailCountRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return AdminUpdateRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    AdminUpdateRequestModel adminUpdateRequestModel = (AdminUpdateRequestModel) target;

    if (adminUpdateRequestModel.getAdminUid() <= 0) {
      errors.reject("adminUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "adminUid"));
    }
    if (adminUpdateRequestModel.getLoginFailCount() < 0) {
      errors.reject("loginFailCount.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "loginFailCount"));
    }
  }
}
