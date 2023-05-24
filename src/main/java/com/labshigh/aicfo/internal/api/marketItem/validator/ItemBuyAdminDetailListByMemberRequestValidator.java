package com.labshigh.aicfo.internal.api.marketItem.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminDetailListByMemberRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminDetailListRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemBuyAdminDetailListByMemberRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemBuyAdminDetailListRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemBuyAdminDetailListByMemberRequestModel requestModel = (ItemBuyAdminDetailListByMemberRequestModel) target;

    if (requestModel.getItemUid() < 1) {
      errors.reject("itemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "itemUid"));
    }
//    if (requestModel.getMemberUid() < 1) {
//      errors.reject("memberUid.required",
//          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
//    }

  }
}
