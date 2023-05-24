package com.labshigh.aicfo.internal.api.marketItem.validator;


import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuySettlementUpdateWithdrawalApprovalFlagRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemBuySettlementUpdateWithdrawalApprovalRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemBuySettlementUpdateWithdrawalApprovalFlagRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemBuySettlementUpdateWithdrawalApprovalFlagRequestModel requestModel = (ItemBuySettlementUpdateWithdrawalApprovalFlagRequestModel) target;

    if (requestModel.getUid() < 1) {
      errors.reject("uid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uid"));
    }


  }
}
