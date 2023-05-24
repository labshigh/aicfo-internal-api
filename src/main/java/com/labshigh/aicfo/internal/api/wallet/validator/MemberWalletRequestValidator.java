package com.labshigh.aicfo.internal.api.wallet.validator;


import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.wallet.model.request.MemberWalletRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberWalletRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberWalletRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberWalletRequestModel requestModel = (MemberWalletRequestModel) target;

    if (requestModel.getMemberUid() < 0) {
      errors.reject("memberId.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberId"));
    }

    if (requestModel.getUserId() == null) {
      errors.reject("userId.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "userId"));
    }

    if (requestModel.getCoinId() <= 0) {
      errors.reject("coinId.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "coinId"));
    }

    if (requestModel.getTokenId() <= 0) {
      errors.reject("tokenId.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "tokenId"));
    }

    if (requestModel.getVisible() == null) {
      errors.reject("visible.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "visible"));
    }
  }
}