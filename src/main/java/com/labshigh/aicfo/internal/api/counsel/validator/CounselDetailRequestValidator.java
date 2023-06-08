package com.labshigh.aicfo.internal.api.counsel.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.counsel.model.request.CounselDetailRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class CounselDetailRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CounselDetailRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CounselDetailRequestModel requestModel = (CounselDetailRequestModel) target;

    if (requestModel.getMemberUid() < 1L) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

    if (requestModel.getCounselUid() < 1L) {
      errors.reject("counselUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "counselUid"));
    }


  }
}
