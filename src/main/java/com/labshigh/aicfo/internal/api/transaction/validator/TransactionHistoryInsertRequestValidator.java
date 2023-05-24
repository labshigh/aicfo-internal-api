package com.labshigh.aicfo.internal.api.transaction.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.transaction.model.request.TransactionHistoryInsertRequestModel;
import com.labshigh.aicfo.internal.api.transaction.model.request.TransactionHistoryWalletInsertRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class TransactionHistoryInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return TransactionHistoryInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    TransactionHistoryWalletInsertRequestModel requestModel = (TransactionHistoryWalletInsertRequestModel) target;

    if (requestModel.getTransactionUid() < 1) {
      errors.reject("transactionUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "transactionUid"));
    }

    if (requestModel.getTokenId() < 1) {
      errors.reject("tokenId.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "tokenId"));
    }

    if (requestModel.getCoinId() < 1) {
      errors.reject("coinId.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "coinId"));
    }
    if (requestModel.getTransactionKindUid() < 1 ) {
      errors.reject("transactionKindUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "transactionKindUid"));
    }
    if (requestModel.getPrice() == null ) {
      errors.reject("price.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "price"));
    }
  }
}
