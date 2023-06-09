package com.labshigh.aicfo.core.models;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.utils.PageUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPageModel {
  @ApiModelProperty(value = "페이지")
  private int page = 1;

  @ApiModelProperty(value = "페이지당 row 개수")
  private int size = Constants.DEFAULT_PAGE_SIZE;

  private PageUtils.OffsetAndRowCount offsetAndRowCount = PageUtils.convertPageToOffset(page, size);

  public void setPage(int page) {
    this.page = page;
    this.offsetAndRowCount = PageUtils.convertPageToOffset(page, size);
  }

  public void setSize(int size) {
    this.size = size;
    this.offsetAndRowCount = PageUtils.convertPageToOffset(page, size);
  }

}