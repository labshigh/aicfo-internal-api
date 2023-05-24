package com.labshigh.aicfo.internal.api.item.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.item.model.request.ItemInsertRequestModel_bak;
import java.math.BigDecimal;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemInsertRequestValidator_bak implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemInsertRequestModel_bak.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemInsertRequestModel_bak requestModel = (ItemInsertRequestModel_bak) target;

    if (StringUtils.isEmpty(requestModel.getName())) {
      errors.reject("name.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "name"));
    }

    if (requestModel.getAdminWalletUid() <= 0) {
      errors.reject("adminWalletUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "adminWalletUid"));
    }
    if (requestModel.getQuantity() <= 0) {
      errors.reject("quantity.required",
          Constants.MSG_ITEM_QUANTITY_ERROR);
    }
    if (requestModel.getPrice().compareTo(BigDecimal.ONE) < 0) {
      errors.reject("price.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "price"));
    }
    String imageUri = requestModel.getImageUri();

    if (StringUtils.isEmpty(imageUri)) {
      errors.reject("imageUri.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "imageUri"));
    }

    if (!StringUtils.isEmpty(imageUri) && !(imageUri.startsWith("https://") || imageUri.startsWith(
        "http://"))) {
      errors.reject("imageUri.validate", Constants.MSG_WRONG_URL);
    }
  }
}
