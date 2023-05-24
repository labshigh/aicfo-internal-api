package com.labshigh.aicfo.internal.api.exchange.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.exchange.model.request.ExchangeGetRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ExchangeGetRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ExchangeGetRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ExchangeGetRequestModel requestModel = (ExchangeGetRequestModel) target;

    if (StringUtils.isEmpty(requestModel.getExchangeName())) {
      errors.reject("exchangeName.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "exchangeName"));
    }
  }
}
