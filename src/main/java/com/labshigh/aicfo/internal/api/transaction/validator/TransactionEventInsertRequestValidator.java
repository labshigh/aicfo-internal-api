package com.labshigh.aicfo.internal.api.transaction.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.transaction.model.request.TransactionEventWalletInsertRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class TransactionEventInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return TransactionEventWalletInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    TransactionEventWalletInsertRequestModel requestModel = (TransactionEventWalletInsertRequestModel) target;

    if (requestModel.getTransactionId() < 1) {
      errors.reject("transactionUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "transactionUid"));
    }

    if (requestModel.getTokenId() < 1) {
      errors.reject("tokenId.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "tokenId"));
    }
    if (requestModel.getTransactionType() == null ) {
      errors.reject("getTransactionType.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "getTransactionType"));
    }
    if (requestModel.getAmount() == null ) {
      errors.reject("price.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "price"));
    }
  }
}
