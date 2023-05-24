package com.labshigh.aicfo.internal.api.member.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.member.model.request.MemberSigninRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberSigninRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberSigninRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberSigninRequestModel memberSigninRequestModel = (MemberSigninRequestModel) target;

    if (StringUtils.isEmpty(memberSigninRequestModel.getEmail())) {
      errors.reject("email.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "email"));
    }
    if (StringUtils.isEmpty(memberSigninRequestModel.getPassword())) {
      errors.reject("password.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "password"));
    }
  }
}