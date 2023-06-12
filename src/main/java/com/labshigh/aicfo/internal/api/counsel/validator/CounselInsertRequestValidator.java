package com.labshigh.aicfo.internal.api.counsel.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.counsel.model.request.CounselInsertRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class CounselInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CounselInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CounselInsertRequestModel requestModel = (CounselInsertRequestModel) target;

    if (requestModel.getCounselKindCommonCodeUid() < 1L) {
      errors.reject("counselKindCommonCodeUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "counselKindCommonCodeUid"));
    }
    if (requestModel.getCounselReservationCommonCodeUid() < 1L) {
      errors.reject("counselReservationCommonCodeUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "counselReservationCommonCodeUid"));
    }
    if (requestModel.getCounselPaymentCommonCodeUid() < 1L) {
      errors.reject("counselPaymentCommonCodeUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "counselPaymentCommonCodeUid"));
    }

    if (requestModel.getCfoUid() < 1L) {
      errors.reject("cfoUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "cfoUid"));
    }

    if (requestModel.getCounselReservationCommonCodeUid() == 12L && (
        !requestModel.isPhoneVerifiedFlag() || StringUtils.isEmpty(
            requestModel.getPhoneNumber()))) {
      errors.reject("phoneNumber.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "phoneNumber"));
    }

    /*if (requestModel.getCounselAt() == null ||
        requestModel.getCounselAt().isBefore(LocalDateTime.now())) {
      errors.reject("counselAt.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "counselAt"));
    }*/

    if (requestModel.getCounselAt() == null) {
      errors.reject("counselAt.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "counselAt"));
    }

    if (requestModel.getMemberUid() < 1L) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

    if (StringUtils.isEmpty(requestModel.getContent())) {
      errors.reject("content.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "content"));
    }

  }
}
