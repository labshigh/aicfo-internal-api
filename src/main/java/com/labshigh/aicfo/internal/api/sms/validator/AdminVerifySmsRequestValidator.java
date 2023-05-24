package com.labshigh.aicfo.internal.api.sms.validator;


import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.sms.model.request.AdminVerifySmsRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class AdminVerifySmsRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return AdminVerifySmsRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    AdminVerifySmsRequestModel requestModel = (AdminVerifySmsRequestModel) target;

    if (requestModel.getAdminUid() <= 0) {
      errors.reject("adminUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "adminUid"));
    }

    if (StringUtils.isEmpty(requestModel.getMenuId().trim())) {
      errors.reject("menuId.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "menuId"));
    }

    if (StringUtils.isEmpty(requestModel.getNationalCode())) {
      errors.reject("nationalCode.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "nationalCode"));
    }

    if (StringUtils.isEmpty(requestModel.getMobile().trim())) {
      errors.reject("phoneNumber.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "phoneNumber"));
    }

    if (StringUtils.isEmpty(requestModel.getVerifyCode().trim())) {
      errors.reject("verifyCode.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "verifyCode"));
    }

  }
}