package com.labshigh.aicfo.internal.api.totp.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.totp.model.request.TotpDeleteRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class TotpDeleteRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return TotpDeleteRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    TotpDeleteRequestModel requestModel = (TotpDeleteRequestModel) target;

    if (requestModel.getMemberUid() <= 0) {
      errors.reject("memberUid.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }
  }
}