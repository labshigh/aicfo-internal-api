package com.labshigh.aicfo.internal.api.member.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberSigninRequestModel {

  private String email;
  private String password;
  private String ip;
}