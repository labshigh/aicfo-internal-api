package com.labshigh.aicfo.internal.api.member.validator;


import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.member.model.request.MemberApporveRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberApproveRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberApporveRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberApporveRequestModel requestModel = (MemberApporveRequestModel) target;

    if (requestModel.getUid() <= 0) {
      errors.reject("uid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uid"));
    }
    if (requestModel.getApproveId() <= 0) {
      errors.reject("approveId.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "approveId"));
    }
    if (requestModel.getEmail() == null ) {
      errors.reject("email.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "email"));
    }
    if (requestModel.getApproveType() == null) {
      errors.reject("approveType.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "approveType"));
    }


  }
}