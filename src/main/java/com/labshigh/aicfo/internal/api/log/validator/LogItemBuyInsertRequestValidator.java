package com.labshigh.aicfo.internal.api.log.validator;


import com.labshigh.aicfo.internal.api.log.model.request.LogItemBuyInsertRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class LogItemBuyInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return LogItemBuyInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    LogItemBuyInsertRequestModel requestModel = (LogItemBuyInsertRequestModel) target;
  }
}
