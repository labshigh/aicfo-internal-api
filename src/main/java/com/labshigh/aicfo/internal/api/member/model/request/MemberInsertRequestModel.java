package com.labshigh.aicfo.internal.api.member.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberInsertRequestModel {

  private String email;
  private String password;
  private String name;
  private String referrer;
}