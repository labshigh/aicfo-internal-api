package com.labshigh.aicfo.internal.api.board.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.board.model.request.BoardReplyInsertRequestModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class BoardReplyInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return BoardReplyInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    BoardReplyInsertRequestModel requestModel = (BoardReplyInsertRequestModel) target;

    if (requestModel.getBoardUid() < 1L) {
      errors.reject("boardUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "boardUid"));
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
