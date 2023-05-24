package com.labshigh.aicfo.internal.api.log.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoggingInsertRequestModel {

  public enum AccessType{
    ADMIN, FRONT
  }
  private String ip;
  private AccessType accessType;
  private String httpMethodType;
  private String apiUrl;
  private String apiParameters;
  private String email;


}
