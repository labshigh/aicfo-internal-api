package com.labshigh.aicfo.internal.api.log.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.log.model.request.LoggingInsertRequestModel;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoggingDao {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;

  private boolean deletedFlag;
  private boolean usedFlag;
  private String ip;
  private LoggingInsertRequestModel.AccessType accessType;
  private String httpMethodType;
  private String apiUrl;
  private String apiParameters;
  private String email;
}
