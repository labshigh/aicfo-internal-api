package com.labshigh.aicfo.internal.api.wallet.validator;


import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.wallet.model.request.MemberWithdrawalWalletRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberWalletWithdrawalRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberWithdrawalWalletRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberWithdrawalWalletRequestModel requestModel = (MemberWithdrawalWalletRequestModel) target;

    if (requestModel.getMemberUid() <= 0) {
      errors.reject("memberId.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberId"));
    }
    if (requestModel.getCoinId() <= 0) {
      errors.reject("coinId.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "coinId"));
    }

    if (requestModel.getTokenId() <= 0) {
      errors.reject("tokenId.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "tokenId"));
    }

    if (requestModel.getAddress() == null) {
      errors.reject("address.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "address"));
    }
    if (requestModel.getName() == null) {
      errors.reject("name.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "name"));
    }
  }
}