package com.labshigh.aicfo.internal.api.counsel.model.request;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.utils.PageUtils;
import com.labshigh.aicfo.internal.api.common.utils.PageUtils.OffsetAndRowCount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CounselListRequestModel {

  @ApiModelProperty(value = "memberUid")
  private long memberUid;

  @ApiModelProperty(value = "정렬")
  private SortType sort;

  @ApiModelProperty(value = "페이지")
  private int page = 1;

  @ApiModelProperty(value = "페이지당 row 개수")
  private int size = Constants.DEFAULT_PAGE_SIZE;

  private OffsetAndRowCount offsetAndRowCount = PageUtils.convertPageToOffset(page, size);

  public void setPage(int page) {
    this.page = page;
    this.offsetAndRowCount = PageUtils.convertPageToOffset(page, size);
  }

  public void setSize(int size) {
    this.size = size;
    this.offsetAndRowCount = PageUtils.convertPageToOffset(page, size);
  }

  public enum SortType {
    PAYMENTLATEST,
    PAYMENTOLDEST,
    COUNSELATLATEST,
    COUNSELATOLDEST
  }


}

