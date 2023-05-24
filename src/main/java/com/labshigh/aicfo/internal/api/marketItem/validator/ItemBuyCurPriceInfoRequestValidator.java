package com.labshigh.aicfo.internal.api.marketItem.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyCurPriceInfoRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemBuyCurPriceInfoRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemBuyCurPriceInfoRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemBuyCurPriceInfoRequestModel requestModel = (ItemBuyCurPriceInfoRequestModel) target;

    if (requestModel.getMemberUid() <= 0) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

  }
}
