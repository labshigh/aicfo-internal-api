package com.labshigh.aicfo.internal.api.marketItem.validator;


import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuySettlementDeleteWithdrawalRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuySettlementDeleteWithdrawalRequestModel.RequestType;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemBuySettlementDeleteWithdrawalRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemBuySettlementDeleteWithdrawalRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemBuySettlementDeleteWithdrawalRequestModel requestModel = (ItemBuySettlementDeleteWithdrawalRequestModel) target;

    if (requestModel.getUid() < 1) {
      errors.reject("uid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uid"));
    }

    if (requestModel.getRequestType() == null) {
      errors.reject("requestType.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "requestType"));
    }
    if (requestModel.getRequestType().equals(RequestType.MEMBER)) {
      if (StringUtils.isEmpty(requestModel.getUserId())) {
        errors.reject("userId.required",
            String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "userId"));
      }
    }
  }
}
