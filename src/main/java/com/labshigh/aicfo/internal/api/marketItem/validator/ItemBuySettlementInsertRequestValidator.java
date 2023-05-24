package com.labshigh.aicfo.internal.api.marketItem.validator;


import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuySettlementInsertRequestModel;
import java.math.BigDecimal;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemBuySettlementInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemBuySettlementInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemBuySettlementInsertRequestModel requestModel = (ItemBuySettlementInsertRequestModel) target;

    if (requestModel.getUid() < 1) {
      errors.reject("uid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uid"));
    }
    if (requestModel.getMemberUid() < 1) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "marketItemUid"));
    }

    if (requestModel.getSettlementType() == null) {
      errors.reject("settlementType.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "settlementType"));
    }
    if (requestModel.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
      errors.reject("price.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "price"));
    }


  }
}
