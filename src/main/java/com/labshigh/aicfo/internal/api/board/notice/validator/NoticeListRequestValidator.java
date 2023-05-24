package com.labshigh.aicfo.internal.api.board.notice.validator;

import com.labshigh.aicfo.core.models.RequestPageModel;
import com.labshigh.aicfo.internal.api.board.notice.model.response.NoticeResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Builder
public class NoticeListRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {return NoticeResponseModel.class.equals(clazz);}

    @Override
    public void validate(Object target, Errors errors) {
        RequestPageModel requestPageModel = (RequestPageModel) target;

        if (requestPageModel.getPage() <= 0) {
            errors.reject("page.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "page"));
        }

        if (requestPageModel.getSize() <= 0) {
            errors.reject("size.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "size"));
        }

        if (requestPageModel.getSize() > Constants.MAX_LIST_PAGE_SIZE) {
            errors.reject("size.lengthOver",
                    String.format(Constants.MSG_MAX_LENGTH_OVER_ERROR, Constants.MAX_LIST_PAGE_SIZE));
        }
    }
}
