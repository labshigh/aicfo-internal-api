package com.labshigh.aicfo.internal.api.commonCode.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonCodeListRequestModel {

  private long commonCodeUid;
  private String lang;
  private String name;
  private Boolean usedFlag;
}