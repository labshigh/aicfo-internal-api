package com.labshigh.aicfo.internal.api.member.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.member.model.request.MemberUpdateCodeRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberUpdateCodeRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberUpdateCodeRequestValidator.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberUpdateCodeRequestModel requestModel = (MemberUpdateCodeRequestModel) target;
    if (requestModel.getEmail() == null) {
      errors.reject("email.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "email"));
    }
  }
}
