package com.labshigh.aicfo.internal.api.member.validator;


import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.member.model.request.MemberSendSmsVerifyRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberSendSmsVerifyRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberSendSmsVerifyRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberSendSmsVerifyRequestModel memberSendSmsVerifyRequestModel = (MemberSendSmsVerifyRequestModel) target;

    if (memberSendSmsVerifyRequestModel.getMemberUid() < 1) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

    if (StringUtils.isEmpty(memberSendSmsVerifyRequestModel.getNationalCode())) {
      errors.reject("nationalCode.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "nationalCode"));
    }

    if (StringUtils.isEmpty(memberSendSmsVerifyRequestModel.getPhoneNumber())) {
      errors.reject("phoneNumber.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "phoneNumber"));
    }

  }
}
