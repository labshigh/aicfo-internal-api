package com.labshigh.aicfo.internal.api.transaction.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.transaction.model.request.TransactionHistoryRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class TransactionHistoryRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return TransactionHistoryRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    TransactionHistoryRequestModel requestModel = (TransactionHistoryRequestModel) target;

    if (requestModel.getMemberUid() < 1) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

    if (StringUtils.isEmpty(requestModel.getUserId())) {
      errors.reject("userId.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "userId"));
    }

    if (requestModel.getStartAt() == null || requestModel.getEndAt() == null) {
      errors.reject("periodDate.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "startAt, endAt"));
    }

    if (requestModel.getPage() <= 0) {
      errors.reject("page.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "page"));
    }

    if (requestModel.getSize() <= 0) {
      errors.reject("size.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "size"));
    }

    if (requestModel.getSize() > Constants.MAX_LIST_PAGE_SIZE) {
      errors.reject("size.lengthOver",
          String.format(Constants.MSG_MAX_LENGTH_OVER_ERROR, Constants.MAX_LIST_PAGE_SIZE));
    }

  }
}
