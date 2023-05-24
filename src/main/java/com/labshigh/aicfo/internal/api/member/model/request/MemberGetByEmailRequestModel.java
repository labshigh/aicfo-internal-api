package com.labshigh.aicfo.internal.api.member.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MemberGetByEmailRequestModel {

  private String email;
}