package com.labshigh.aicfo.internal.api.member.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdateReferrerRequestModel {

  @ApiModelProperty(value = "사용자 아이디")
  private Long memberUid;

  @ApiModelProperty(value = "referrer")
  private String referrer;

  @ApiModelProperty(value = "walletId")
  private Long walletId;

  @ApiModelProperty(value = "email")
  private String email;
}
