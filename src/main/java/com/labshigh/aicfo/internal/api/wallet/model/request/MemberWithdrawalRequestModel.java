package com.labshigh.aicfo.internal.api.wallet.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberWithdrawalRequestModel {

  @ApiModelProperty(value = "uid")
  private Long uid;
  @ApiModelProperty(value = "email")
  private String email;
  @ApiModelProperty(value = "address")
  private String address;
  @ApiModelProperty(value = "코인 아이디")
  private Long coinId;
  @ApiModelProperty(value = "토큰 아이디")
  private Long tokenId;
  @ApiModelProperty(value = "멤버 아이디")
  private Long memberUid;
  
}
