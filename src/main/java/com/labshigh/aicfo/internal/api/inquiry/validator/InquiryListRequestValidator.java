package com.labshigh.aicfo.internal.api.inquiry.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.inquiry.model.request.InquiryListRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class InquiryListRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return InquiryListRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    InquiryListRequestModel requestModel = (InquiryListRequestModel) target;

    if (requestModel.getMemberUid() < 1) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

    if (requestModel.getPage() <= 0) {
      errors.reject("page.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "page"));
    }

    if (requestModel.getSize() <= 0) {
      errors.reject("size.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "size"));
    }

    if (requestModel.getSize() > Constants.MAX_LIST_PAGE_SIZE) {
      errors.reject("size.lengthOver",
          String.format(Constants.MSG_MAX_LENGTH_OVER_ERROR, Constants.MAX_LIST_PAGE_SIZE));
    }

  }
}
