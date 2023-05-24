package com.labshigh.aicfo.internal.api.member.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberCodeResponseModel {
  private String email;
  private String code;
}