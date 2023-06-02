package com.labshigh.aicfo.internal.api.board.validator;

import com.labshigh.aicfo.internal.api.board.model.request.BoardReplyListRequestModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class BoardReplyListRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return BoardReplyListRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    BoardReplyListRequestModel requestModel = (BoardReplyListRequestModel) target;

    if (requestModel.getBoardUid() < 1L) {
      errors.reject("boardUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "boardUid"));
    }

    if (requestModel.getPage() <= 0) {
      errors.reject("page.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "page"));
    }

    if (requestModel.getSize() <= 0) {
      errors.reject("size.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "size"));
    }

    if (requestModel.getSize() > Constants.MAX_LIST_PAGE_SIZE) {
      errors.reject("size.lengthOver",
          String.format(Constants.MSG_MAX_LENGTH_OVER_ERROR, Constants.MAX_LIST_PAGE_SIZE));
    }

  }
}
