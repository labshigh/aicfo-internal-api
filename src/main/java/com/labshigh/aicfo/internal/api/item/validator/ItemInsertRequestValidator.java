package com.labshigh.aicfo.internal.api.item.validator;


import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.item.model.request.ItemInsertRequestModel;
import java.math.BigDecimal;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemInsertRequestModel requestModel = (ItemInsertRequestModel) target;

    if (StringUtils.isEmpty(requestModel.getRound())) {
      errors.reject("title.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "title"));
    }

    if (StringUtils.isEmpty(requestModel.getInterest())) {
      errors.reject("interest.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "interest"));
    }
    if (requestModel.getStartAt() == null) {
      errors.reject("startAt.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "startAt"));
    }
    if (requestModel.getEndAt() == null) {
      errors.reject("endAt.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "endAt"));
    }

    if (requestModel.getRequestEndAt() == null) {
      errors.reject("requestEndAt.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "requestEndAt"));
    }
    if (requestModel.getWithdrawalRequestEndAt() == null) {
      errors.reject("withdrawalRequestEndAt.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "withdrawalRequestEndAt"));
    }
    if (requestModel.getMinPrice() == null
        || requestModel.getMinPrice().compareTo(BigDecimal.ZERO) < 0) {
      errors.reject("minPrice.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "minPrice"));
    }

    if (requestModel.getAutoItemUid() < 1) {
      errors.reject("autoItemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "autoItemUid"));
    }

    if (requestModel.getItemKind() != 2) {
      errors.reject("itemKind.validate", Constants.MSG_VALUE_ERROR);
    }
  }
}
