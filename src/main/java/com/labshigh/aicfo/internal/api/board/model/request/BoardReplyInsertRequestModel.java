package com.labshigh.aicfo.internal.api.board.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardReplyInsertRequestModel {

  private String content;
  private long boardUid;
  private long memberUid;


}

