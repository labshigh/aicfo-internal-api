package com.labshigh.aicfo.internal.api.item.validator;

import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.item.model.request.ItemSortUpdateRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemSortUpdateRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemSortUpdateRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemSortUpdateRequestModel requestModel = (ItemSortUpdateRequestModel) target;

    if (requestModel.getUid() <= 0) {
      errors.reject("itemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "itemUid"));
    }

    if (StringUtils.isEmpty(requestModel.getSortValue())) {
      errors.reject("sort.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "sort"));
    }
//
//    if (requestModel.getSize() > Constants.MAX_LIST_PAGE_SIZE) {
//      errors.reject("size.lengthOver", String.format(Constants.MSG_MAX_LENGTH_OVER_ERROR, "size"));
//    }

  }
}
