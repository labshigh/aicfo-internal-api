package com.labshigh.aicfo.internal.api.board.validator;

import com.labshigh.aicfo.internal.api.board.model.request.BoardReplyDeleteRequestModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class BoardReplyDeleteRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return BoardReplyDeleteRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    BoardReplyDeleteRequestModel requestModel = (BoardReplyDeleteRequestModel) target;

    if (requestModel.getUid() < 1L) {
      errors.reject("uid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uid"));
    }

    if (requestModel.getBoardUid() < 1L) {
      errors.reject("boardUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "boardUid"));
    }

    if (requestModel.getMemberUid() < 1L) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

  }
}
