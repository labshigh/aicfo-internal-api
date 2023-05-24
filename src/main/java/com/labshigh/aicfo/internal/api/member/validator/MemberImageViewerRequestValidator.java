package com.labshigh.aicfo.internal.api.member.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.utils.enums.FileType;
import com.labshigh.aicfo.internal.api.member.model.request.MemberImageViewerRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberImageViewerRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberImageViewerRequestValidator.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberImageViewerRequestModel requestModel = (MemberImageViewerRequestModel) target;
    if (requestModel.getEmail() == null) {
      errors.reject("email.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "email"));
    }

    if (requestModel.getFileType() == null) {
      errors.reject("fileType.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "fileType"));
    }

    if (!( requestModel.getFileType().name().equals(FileType.member_code.name()) || requestModel.getFileType().name().equals(FileType.member_jumin.name()) )) {
      errors.reject("fileType.incorrect",
        String.format(Constants.MSG_VALUE_ERROR, "fileType"));
    }
  }
}
