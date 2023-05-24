package com.labshigh.aicfo.internal.api.staking.validator;


import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.staking.model.request.AdminWalletInsertRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class AdminWalletInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return AdminWalletInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    AdminWalletInsertRequestModel requestModel = (AdminWalletInsertRequestModel) target;

    if (requestModel.getAdminUid() < 1) {
      errors.reject("adminUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "adminUid"));
    }

    if (StringUtils.isEmpty(requestModel.getWalletAddress())) {
      errors.reject("walletAddress.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "walletAddress"));
    }

  }
}
