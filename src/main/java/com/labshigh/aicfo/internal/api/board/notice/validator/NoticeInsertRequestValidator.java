package com.labshigh.aicfo.internal.api.board.notice.validator;

import com.labshigh.aicfo.internal.api.board.notice.model.request.NoticeInsertRequestModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class NoticeInsertRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {return NoticeInsertRequestModel.class.equals(clazz); }

    @Override
    public void validate(Object target, Errors errors) {
        NoticeInsertRequestModel requestModel = (NoticeInsertRequestModel) target;

        if(!StringUtils.hasText(requestModel.getTitle())) {
            errors.reject("title.required",
                    String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "title"));
        }

        if(!StringUtils.hasText(requestModel.getContent())) {
            errors.reject("content.required",
                    String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "content"));
        }

        if(requestModel.getNoticeTypeUid() <= 0) {
            errors.reject("type.required",
                    String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "type"));
        }

    }
}
