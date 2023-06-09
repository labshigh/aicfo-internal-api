package com.labshigh.aicfo.internal.api.member.validator;

import com.labshigh.aicfo.core.models.RequestPageModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.member.model.request.MemberListRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberListRequestValidator implements Validator{

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberListRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    RequestPageModel requestPageModel = (RequestPageModel) target;

    if (requestPageModel.getPage() <= 0) {
      errors.reject("page.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "page"));
    }

    if (requestPageModel.getSize() <= 0) {
      errors.reject("size.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "size"));
    }

    if (requestPageModel.getSize() > Constants.MAX_LIST_PAGE_SIZE) {
      errors.reject("size.lengthOver",
        String.format(Constants.MSG_MAX_LENGTH_OVER_ERROR, Constants.MAX_LIST_PAGE_SIZE));
    }

  }
}