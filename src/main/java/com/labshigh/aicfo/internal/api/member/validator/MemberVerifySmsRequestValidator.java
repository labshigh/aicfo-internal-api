package com.labshigh.aicfo.internal.api.member.validator;


import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.member.model.request.MemberVerifySmsRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberVerifySmsRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberVerifySmsRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberVerifySmsRequestModel requestModel = (MemberVerifySmsRequestModel) target;

    if (requestModel.getMemberUid() <= 0) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

    if (StringUtils.isEmpty(requestModel.getNationalCode())) {
      errors.reject("nationalCode.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "nationalCode"));
    }
    if (StringUtils.isEmpty(requestModel.getPhoneNumber().trim())) {
      errors.reject("phoneNumber.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "phoneNumber"));
    }

    if (StringUtils.isEmpty(requestModel.getVerifyCode().trim())) {
      errors.reject("verifyCode.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "verifyCode"));
    }
  }
}