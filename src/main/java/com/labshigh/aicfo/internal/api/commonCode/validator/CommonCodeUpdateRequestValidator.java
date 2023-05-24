package com.labshigh.aicfo.internal.api.commonCode.validator;

import com.labshigh.aicfo.internal.api.commonCode.model.request.CommonCodeUpdateRequestModel;
import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class CommonCodeUpdateRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CommonCodeUpdateRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CommonCodeUpdateRequestModel commonCodeUpdateRequestModel = (CommonCodeUpdateRequestModel) target;

    if (commonCodeUpdateRequestModel.getCommonCodeUid() <= 0) {
      errors.reject("commonCodeUid.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "commonCodeUid"));
    }
    if (StringUtils.isEmpty(commonCodeUpdateRequestModel.getName())) {
      errors.reject("name.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "name"));
    }
    if (commonCodeUpdateRequestModel.getSort() <= 0) {
      errors.reject("sort.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "sort"));
    }
  }
}