package com.labshigh.aicfo.internal.api.commonCode.validator;

import com.labshigh.aicfo.internal.api.commonCode.model.request.CommonCodeInsertRequestModel;
import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class CommonCodeInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CommonCodeInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CommonCodeInsertRequestModel commonCodeInsertRequestModel = (CommonCodeInsertRequestModel) target;

    if (StringUtils.isEmpty(commonCodeInsertRequestModel.getName())) {
      errors.reject("name.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "name"));
    }
    if (commonCodeInsertRequestModel.getSort() <= 0) {
      errors.reject("sort.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "sort"));
    }
  }
}