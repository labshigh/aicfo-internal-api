package com.labshigh.aicfo.internal.api.item.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemMetaDataModel {

  private String imageUri;
  private String name;
  private boolean midSaleFlag;
  private String interest;
  private String description;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime startAt;

}
