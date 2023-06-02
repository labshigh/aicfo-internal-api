package com.labshigh.aicfo.internal.api.board.validator;

import com.labshigh.aicfo.internal.api.board.model.request.BoardListRequestModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class BoardListRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return BoardListRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    BoardListRequestModel requestModel = (BoardListRequestModel) target;

    if (requestModel.getBoardTypeCommonCodeUid() < 1) {
      errors.reject("boardTypeCommonCodeUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "boardTypeCommonCodeUid"));
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
