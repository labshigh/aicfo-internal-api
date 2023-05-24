package com.labshigh.aicfo.internal.api.marketItem.validator;


import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyInsertRequestModel;
import java.math.BigDecimal;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemBuyInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemBuyInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemBuyInsertRequestModel requestModel = (ItemBuyInsertRequestModel) target;

    if (requestModel.getItemUid() < 1) {
      errors.reject("itemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "itemUid"));
    }
    if (requestModel.getPriceUnit() == null) {
      errors.reject("priceUnit.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "priceUnit"));
    }
    if (requestModel.getUserId() == null) {
      errors.reject("userId.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "userId"));
    }
//
//    if (requestModel.getQuantity() < 1) {
//      errors.reject("quantity.required",
//          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "quantity"));
//    }

    if (requestModel.getPrice().compareTo(BigDecimal.ZERO) < 0) {
      errors.reject("price.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "price"));
    }
  }
}
