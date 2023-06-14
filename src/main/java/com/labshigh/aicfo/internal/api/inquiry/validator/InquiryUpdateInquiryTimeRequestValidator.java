package com.labshigh.aicfo.internal.api.inquiry.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.inquiry.model.request.InquiryUpdateRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class InquiryUpdateInquiryTimeRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return InquiryUpdateRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    InquiryUpdateRequestModel requestModel = (InquiryUpdateRequestModel) target;

    if (requestModel.getMemberUid() < 1L) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

    if (StringUtils.isEmpty(requestModel.getInquiryTime())) {
      errors.reject("inquiryTime.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "inquiryTime"));
    }

    if (requestModel.getUid() < 1L) {
      errors.reject("uid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uid"));
    }

  }
}
