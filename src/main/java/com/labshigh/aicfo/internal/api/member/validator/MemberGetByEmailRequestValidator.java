package com.labshigh.aicfo.internal.api.member.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.member.model.request.MemberGetByEmailRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberGetByEmailRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberGetByEmailRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberGetByEmailRequestModel requestModel = (MemberGetByEmailRequestModel) target;

    if (StringUtils.isEmpty(requestModel.getEmail())) {
      errors.reject("email.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "email"));
    }
    //회원가입 여부 체크시 kind = -1
    /*if (requestModel.getKind() <= 0) {
      errors.reject("kind.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "kind"));
    }*/
  }
}