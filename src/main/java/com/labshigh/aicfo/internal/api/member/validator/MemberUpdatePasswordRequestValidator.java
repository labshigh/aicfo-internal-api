package com.labshigh.aicfo.internal.api.member.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.member.model.request.MemberUpdatePasswordRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberUpdatePasswordRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberUpdatePasswordRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberUpdatePasswordRequestModel requestModel = (MemberUpdatePasswordRequestModel) target;
    if (requestModel.getMemberUid() <= 0) {
      errors.reject("memberUid.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }
    if (StringUtils.isEmpty(requestModel.getPassword())) {
      errors.reject("password.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "password"));
    }
    if (requestModel.getCurrentPassword().equals(requestModel.getPassword())) {
      errors.reject("password.required", String.format(Constants.MSG_PASSWORD_SAME_ERROR));
    }
  }
}