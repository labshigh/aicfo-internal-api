package com.labshigh.aicfo.internal.api.board.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardReplyDao {

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
