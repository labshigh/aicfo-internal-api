package com.labshigh.aicfo.internal.api.marketItem.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemMyReferrerRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MarketItemMyReferrerRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MarketItemMyReferrerRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MarketItemMyReferrerRequestModel requestModel = (MarketItemMyReferrerRequestModel) target;

    if (StringUtils.isEmpty(requestModel.getUserId())) {
      errors.reject("userId.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "userId"));
    }

    if (requestModel.getItemUid() < 1) {
      errors.reject("itemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "itemUid"));
    }

  }
}
