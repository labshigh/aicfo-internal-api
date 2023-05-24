package com.labshigh.aicfo.internal.api.member.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.member.model.request.SigninWalletRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class SigninWalletRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return SigninWalletRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    SigninWalletRequestModel requestModel = (SigninWalletRequestModel) target;

    if (requestModel.getMemberUid() < 1 ) {
      errors.reject("memberUid.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }
    if (requestModel.getWalletId() < 1 ) {
      errors.reject("walletId.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "walletId"));
    }
    if (requestModel.getMemberUid() < 1 ) {
      errors.reject("referrerCode.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "referrerCode"));
    }
  }
}