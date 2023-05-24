package com.labshigh.aicfo.internal.api.member.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.member.model.request.MemberUpdateReferrerRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberUpdateReferrerCodeRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberUpdateReferrerRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberUpdateReferrerRequestModel requestModel = (MemberUpdateReferrerRequestModel) target;
    if (requestModel.getMemberUid() <= 0) {
      errors.reject("memberUid.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }
  }
}