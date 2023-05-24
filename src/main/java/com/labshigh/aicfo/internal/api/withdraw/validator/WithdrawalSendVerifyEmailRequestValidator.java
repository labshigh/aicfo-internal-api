package com.labshigh.aicfo.internal.api.withdraw.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.member.model.request.MemberSendVerifyEmailRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class WithdrawalSendVerifyEmailRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberSendVerifyEmailRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberSendVerifyEmailRequestModel requestModel = (MemberSendVerifyEmailRequestModel) target;
    if (requestModel.getMemberWalletWithdrawalUid() < 0) {
      errors.reject("memberWalletWithdrawalUid.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberWalletWithdrawalUid"));
    }

  }
}
