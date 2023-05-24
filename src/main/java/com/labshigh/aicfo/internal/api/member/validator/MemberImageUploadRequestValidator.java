package com.labshigh.aicfo.internal.api.member.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.utils.enums.FileType;
import com.labshigh.aicfo.internal.api.member.model.request.MemberImageUploadRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberImageUploadRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberImageUploadRequestValidator.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberImageUploadRequestModel requestModel = (MemberImageUploadRequestModel) target;
    if (requestModel.getEmail() == null) {
      errors.reject("email.required",
        String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "email"));
    }

    if (requestModel.getFile() == null) {
      errors.reject("multipartFile.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "multipartFile"));
    }

    if (requestModel.getFileType() == null) {
      errors.reject("fileType.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "fileType"));
    }

                          if (!( FileType.member_code.name().equals( requestModel.getFileType().name())  || FileType.member_jumin.name().equals(requestModel.getFileType().name() ))) {
      errors.reject("fileType.incorrect",
        String.format(Constants.MSG_VALUE_ERROR, "fileType"));
    }
  }
}
