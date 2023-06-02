package com.labshigh.aicfo.internal.api.board.validator;

import com.labshigh.aicfo.internal.api.board.model.request.BoardDetailRequestModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class BoardDetailRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return BoardDetailRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    BoardDetailRequestModel requestModel = (BoardDetailRequestModel) target;

    if (requestModel.getBoardUid() < 1) {
      errors.reject("boardUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "boardUid"));
    }

    if (requestModel.getBoardTypeCommonCodeUid() < 1) {
      errors.reject("boardTypeCommonCodeUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "boardTypeCommonCodeUid"));
    }


  }
}
