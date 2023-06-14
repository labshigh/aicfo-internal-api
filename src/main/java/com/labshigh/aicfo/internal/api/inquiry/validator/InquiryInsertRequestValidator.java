package com.labshigh.aicfo.internal.api.inquiry.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.inquiry.model.request.InquiryInsertRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class InquiryInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return InquiryInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    InquiryInsertRequestModel requestModel = (InquiryInsertRequestModel) target;

    if (requestModel.getMemberUid() < 1L) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

    if (requestModel.getInquiryCommonCodeUid() < 1L) {
      errors.reject("inquiryCommonCodeUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "inquiryCommonCodeUid"));
    }

    if (requestModel.getCfoUid() < 1L) {
      errors.reject("cfoUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "cfoUid"));
    }

  }
}
