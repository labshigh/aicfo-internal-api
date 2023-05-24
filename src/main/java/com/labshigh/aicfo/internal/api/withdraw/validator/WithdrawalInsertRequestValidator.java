package com.labshigh.aicfo.internal.api.withdraw.validator;


import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.withdraw.model.request.WithdrawInsertRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Builder
public class WithdrawalInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return WithdrawInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    WithdrawInsertRequestModel requestModel = (WithdrawInsertRequestModel) target;

    if (requestModel.getMemberWalletWithdrawalUid() < 0) {
      errors.reject("memberWalletWithdrawalUid.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberWalletWithdrawalUid"));
    }

    if (requestModel.getMemberUid() < 0) {
      errors.reject("memberId.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberId"));
    }

    if (requestModel.getRequestQuantity().compareTo(BigDecimal.ZERO) < 0) {
      errors.reject("requestQuantity.required",
              String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "requestQuantity"));
    }

  }
}