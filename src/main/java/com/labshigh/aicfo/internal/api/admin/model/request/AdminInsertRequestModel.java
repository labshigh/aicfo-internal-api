package com.labshigh.aicfo.internal.api.admin.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminInsertRequestModel {

  private String adminId;
  private String password;
  private String name;
  private String nationalCode;
  private String mobile;
  private long roleUid;
  private boolean usedFlag;
}
