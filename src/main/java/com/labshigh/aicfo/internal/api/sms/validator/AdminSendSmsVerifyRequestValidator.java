package com.labshigh.aicfo.internal.api.sms.validator;


import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.sms.model.request.AdminSendSmsVerifyRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class AdminSendSmsVerifyRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return AdminSendSmsVerifyRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    AdminSendSmsVerifyRequestModel requestModel = (AdminSendSmsVerifyRequestModel) target;

    if (requestModel.getAdminUid() < 1) {
      errors.reject("adminUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "adminUid"));
    }

    if (StringUtils.isEmpty(requestModel.getMenuId().trim())) {
      errors.reject("menuId.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "menuId"));
    }

//    if (StringUtils.isEmpty(requestModel.getNationalCode().trim())) {
//      errors.reject("nationalCode.required",
//          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "nationalCode"));
//    }
//
//    if (StringUtils.isEmpty(requestModel.getMobile().trim())) {
//      errors.reject("phoneNumber.required",
//          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "phoneNumber"));
//    }

  }
}
