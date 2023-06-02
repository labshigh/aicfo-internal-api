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
public class BoardResponseModel {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private Boolean usedFlag;

  private String title;
  private String content;
  private String uri;
  private long viewCount;
  private long recommendCount;
  private long counselKindCommonCodeUid;
  private String counselKindName;
  private long boardTypeCommonCodeUid;
  private String boardTypeName;
  private boolean topFlag;

}
