package com.labshigh.aicfo.internal.api.staking.validator;


import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.staking.model.request.AdminWalletUpdateRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class AdminWalletUpdateRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return AdminWalletUpdateRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    AdminWalletUpdateRequestModel requestModel = (AdminWalletUpdateRequestModel) target;

    if (requestModel.getUid() < 1) {
      errors.reject("uid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uid"));
    }
    if (requestModel.getAdminUid() < 1) {
      errors.reject("adminUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "adminUid"));
    }
  }
}
