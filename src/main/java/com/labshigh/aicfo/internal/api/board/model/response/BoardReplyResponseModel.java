package com.labshigh.aicfo.internal.api.board.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BoardReplyResponseModel {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private Boolean usedFlag;

  private String content;
  private long boardUid;
  private long memberUid;
  private String nickname;
  private String phoneNumber;
  private String email;

}
