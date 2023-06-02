package com.labshigh.aicfo.internal.api.board.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDetailRequestModel {

  private long boardUid;
  private long memberUid;
  private long boardTypeCommonCodeUid;
  private long counselKindCommonCodeUid;
  private boolean updateViewCount;

}
