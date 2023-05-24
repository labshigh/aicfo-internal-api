package com.labshigh.aicfo.internal.api.marketItem.model.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class ItemBuyListRequestModel {

  private long uid;
  private long itemUid;
  private long memberUid;
  private String userId;
  private String processStatus;

  /*년 기준으로 스테이킹 정보 조회 사용*/
  private Integer year;

  //Staking 정보(수익 정보 조회) 사용
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startAt;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endAt;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate toDay;

/*
  @ApiModelProperty(value = "페이지")
  private int page = 1;

  @ApiModelProperty(value = "페이지당 row 개수")
  private int size = Constants.MAX_LIST_PAGE_SIZE;

  private boolean adminFlag;

  private OffsetAndRowCount offsetAndRowCount = PageUtils.convertPageToOffset(page, size);

  public void setPage(int page) {
    this.page = page;
    this.offsetAndRowCount = PageUtils.convertPageToOffset(page, size);
  }

  public void setSize(int size) {
    this.size = size;
    this.offsetAndRowCount = PageUtils.convertPageToOffset(page, size);
  }

  private enum SortType {
    latest,
    buyCount
  }
  */

}
