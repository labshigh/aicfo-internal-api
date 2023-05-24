package com.labshigh.aicfo.internal.api.marketItem.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemListRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MarketItemListRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MarketItemListRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MarketItemListRequestModel requestModel = (MarketItemListRequestModel) target;

    if (StringUtils.isEmpty(requestModel.getYear())) {
      errors.reject("year.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "year"));
    }

//
//    if (marketItem2ListRequestModel.getPage() <= 0) {
//      errors.reject("page.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "page"));
//    }
//
//    if (marketItem2ListRequestModel.getSize() <= 0) {
//      errors.reject("size.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "size"));
//    }
//
//    if (marketItem2ListRequestModel.getSize() > Constants.MAX_LIST_PAGE_SIZE) {
//      errors.reject("size.lengthOver",
//          String.format(Constants.MSG_MAX_LENGTH_OVER_ERROR, Constants.MAX_LIST_PAGE_SIZE));
//    }

  }
}
