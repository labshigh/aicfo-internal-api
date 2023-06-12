package com.labshigh.aicfo.internal.api.counsel.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.counsel.model.request.CounselUpdateRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class CounselUpdateRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CounselUpdateRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CounselUpdateRequestModel requestModel = (CounselUpdateRequestModel) target;

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
