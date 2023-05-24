package com.labshigh.aicfo.internal.api.staticData.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.staticData.model.request.StaticDataDetailRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class StaticDataDetailRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return StaticDataDetailRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    StaticDataDetailRequestModel requestModel = (StaticDataDetailRequestModel) target;

    if (requestModel.getStaticDataUid() < 0) {
      errors.reject("staticDataUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "staticDataUid"));
    }
  }
}