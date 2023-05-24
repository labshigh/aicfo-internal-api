package com.labshigh.aicfo.internal.api.member.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdateNicknameRequestModel {

  @ApiModelProperty(value = "사용자 아이디")
  private long memberUid;

  @ApiModelProperty(value = "nickname")
  private String nickname;

  @ApiModelProperty(value = "email")
  private String email;
}
