package com.labshigh.aicfo.internal.api.board.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardReplyDeleteRequestModel {

  private long uid;
  private long boardUid;
  private long memberUid;


}

