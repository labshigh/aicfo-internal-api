package com.labshigh.aicfo.internal.api.marketItem.validator;


import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemUpdateRequestModel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MarketItemUpdateRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MarketItemUpdateRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MarketItemUpdateRequestModel requestModel = (MarketItemUpdateRequestModel) target;

    if (requestModel.getUid() < 1) {
      errors.reject("itemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "itemUid"));
    }

    if (Integer.parseInt(requestModel.getInterest()) < 0) {
      errors.reject("interest.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "interest"));
    }
    LocalDateTime now = LocalDateTime.now();
    if (requestModel.getStartAt().isBefore(now)) {
      errors.reject("startAt.Error", Constants.MSG_VALUE_ERROR);
    }
    if (requestModel.getRequestEndAt().isBefore(now)) {
      errors.reject("requestEndAt.Error", Constants.MSG_VALUE_ERROR);
    }
    if (requestModel.getEndAt().isBefore(now)) {
      errors.reject("endAt.Error", Constants.MSG_VALUE_ERROR);
    }

    if (requestModel.getMinPrice().compareTo(BigDecimal.ZERO) < 0) {
      errors.reject("minPrice.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "minPrice"));
    }
  }
}
