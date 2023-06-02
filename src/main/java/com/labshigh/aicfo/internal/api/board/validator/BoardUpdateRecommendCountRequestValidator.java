package com.labshigh.aicfo.internal.api.board.validator;

import com.labshigh.aicfo.internal.api.board.model.request.BoardUpdateRecommendCountRequestModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class BoardUpdateRecommendCountRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return BoardUpdateRecommendCountRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    BoardUpdateRecommendCountRequestModel requestModel = (BoardUpdateRecommendCountRequestModel) target;

    if (requestModel.getBoardUid() < 1) {
      errors.reject("boardUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "boardUid"));
    }

    if (!(requestModel.getIncrement() == 1 || requestModel.getIncrement() == -1)) {
      errors.reject("increment.valueError",
          Constants.MSG_VALUE_ERROR);
    }


  }
}
