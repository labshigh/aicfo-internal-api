package com.labshigh.aicfo.internal.api.marketItem.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminListRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemBuyAdminListRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemBuyAdminListRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemBuyAdminListRequestModel requestModel = (ItemBuyAdminListRequestModel) target;

    if (StringUtils.isEmpty(requestModel.getYear())) {
      errors.reject("year.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "year"));
    }

  }
}
