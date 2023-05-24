package com.labshigh.aicfo.internal.api.totp.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.totp.model.request.TotpDetailRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class TotpDetailRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return TotpDetailRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    TotpDetailRequestModel requestModel = (TotpDetailRequestModel) target;

    if (requestModel.getMemberUid() <= 0) {
      errors.reject("memberUid.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }
  }
}