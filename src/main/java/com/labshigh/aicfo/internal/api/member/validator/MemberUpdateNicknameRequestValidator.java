package com.labshigh.aicfo.internal.api.member.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.member.model.request.MemberUpdateNicknameRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberUpdateNicknameRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberUpdateNicknameRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberUpdateNicknameRequestModel requestModel = (MemberUpdateNicknameRequestModel) target;
    if (requestModel.getMemberUid() <= 0) {
      errors.reject("memberUid.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

    if (StringUtils.isEmpty(requestModel.getNickname())) {
      errors.reject("nickname.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "nickname"));
    }
  }
}