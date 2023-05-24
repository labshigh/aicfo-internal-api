package com.labshigh.aicfo.internal.api.withdraw.validator;


import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.withdraw.model.request.WithdrawUpdateRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class WithdrawalUpdateRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return WithdrawUpdateRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    WithdrawUpdateRequestModel requestModel = (WithdrawUpdateRequestModel) target;

    if (requestModel.getUid() < 0) {
      errors.reject("uid.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uid"));
    }
  }
}