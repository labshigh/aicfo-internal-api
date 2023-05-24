package com.labshigh.aicfo.internal.api.member.validator;

import com.labshigh.aicfo.internal.api.member.model.request.MemberGetByWalletAddressRequestModel;
import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberGetByWalletAddressRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberGetByWalletAddressRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberGetByWalletAddressRequestModel requestModel = (MemberGetByWalletAddressRequestModel) target;

    if (StringUtils.isEmpty(requestModel.getWalletAddress())) {
      errors.reject("walletAddress.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "walletAddress"));
    }
    if (!StringUtils.isEmpty(requestModel.getWalletAddress())
        && requestModel.getWalletAddress().length() != 42) {
      errors.reject("walletAddress.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "walletAddress"));
    }
  }
}
