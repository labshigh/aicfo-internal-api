package com.labshigh.aicfo.internal.api.totp.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.totp.model.request.TotpActivateRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class TotpActivateRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return TotpActivateRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    TotpActivateRequestModel requestModel = (TotpActivateRequestModel) target;

    if (requestModel.getMemberUid() <= 0) {
      errors.reject("memberUid.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }
    if (requestModel.getCode() == null || requestModel.getCode().length() != 6) {
      errors.reject("code.required", Constants.MSG_TOTP_CODE_LENGTH_FIELD_ERROR);
    }
  }
}