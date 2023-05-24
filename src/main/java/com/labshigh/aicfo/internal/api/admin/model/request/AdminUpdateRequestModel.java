package com.labshigh.aicfo.internal.api.admin.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdateRequestModel {

  private long adminUid;
  private String password;
  private String name;
  private long roleUid;
  private String nationalCode;
  private String mobile;
  private boolean usedFlag;
  private int loginFailCount;
}
