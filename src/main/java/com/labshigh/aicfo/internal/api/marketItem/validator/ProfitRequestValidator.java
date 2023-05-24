package com.labshigh.aicfo.internal.api.marketItem.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyListRequestModel;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ProfitRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemBuyListRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemBuyListRequestModel itemBuyListRequestModel = (ItemBuyListRequestModel) target;

    if (itemBuyListRequestModel.getMemberUid() <= 0) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }
    if (itemBuyListRequestModel.getToDay() != null) {
      if (ChronoUnit.DAYS.between(itemBuyListRequestModel.getToDay(), LocalDate.now()) > 0) {
        errors.reject("today.error", Constants.MSG_MARKET_ITEM_TODAY_ERROR);
      }
    }
/*
    if (itemBuyListRequestModel.getPage() <= 0) {
      errors.reject("page.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "page"));
    }

    if (itemBuyListRequestModel.getSize() <= 0) {
      errors.reject("size.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "size"));
    }

    if (itemBuyListRequestModel.getSize() > Constants.MAX_LIST_PAGE_SIZE) {
      errors.reject("size.lengthOver", String.format(Constants.MSG_MAX_LENGTH_OVER_ERROR, "size"));
    }
*/
  }
}
