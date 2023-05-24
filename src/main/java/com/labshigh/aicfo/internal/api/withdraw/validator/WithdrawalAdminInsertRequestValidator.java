package com.labshigh.aicfo.internal.api.withdraw.validator;


import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.withdraw.model.request.WithdrawConfirmInsertRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class WithdrawalAdminInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return WithdrawConfirmInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    WithdrawConfirmInsertRequestModel requestModel = (WithdrawConfirmInsertRequestModel) target;

    if (requestModel.getWithdrawRequestUid() < 0) {
      errors.reject("withdrawRequestUid.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "withdrawRequestUid"));
    }
  }
}