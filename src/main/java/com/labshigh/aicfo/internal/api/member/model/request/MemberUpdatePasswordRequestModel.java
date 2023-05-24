package com.labshigh.aicfo.internal.api.member.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdatePasswordRequestModel {

  private long memberUid;
  private String currentPassword;
  private String password;
  private String email;
  private String token;
}