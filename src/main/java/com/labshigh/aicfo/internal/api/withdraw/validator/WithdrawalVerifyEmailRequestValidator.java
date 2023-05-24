package com.labshigh.aicfo.internal.api.withdraw.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.member.model.request.MemberSendVerifyEmailRequestModel;
import com.labshigh.aicfo.internal.api.withdraw.model.request.WithdrawVerifyEmailRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class WithdrawalVerifyEmailRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberSendVerifyEmailRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    WithdrawVerifyEmailRequestModel requestModel = (WithdrawVerifyEmailRequestModel) target;
    if (requestModel.getMemberWalletWithdrawalUid() < 0) {
      errors.reject("memberWalletWithdrawalUid.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberWalletWithdrawalUid"));
    }

    if (StringUtils.isEmpty(requestModel.getToken())) {
      errors.reject("token.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "token"));
    }

    if (StringUtils.isEmpty(requestModel.getCode())) {
      errors.reject("code.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "code"));
    }

  }
}
