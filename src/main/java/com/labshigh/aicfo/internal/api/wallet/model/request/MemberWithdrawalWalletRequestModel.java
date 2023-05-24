package com.labshigh.aicfo.internal.api.wallet.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberWithdrawalWalletRequestModel {

  @ApiModelProperty(value = "uid")
  private Long uid;
  @ApiModelProperty(value = "email")
  private String email;
  @ApiModelProperty(value = "name")
  private String name;

  @ApiModelProperty(value = "name")
  private String address;
  @ApiModelProperty(value = "코인 아이디")
  private Long coinId;
  @ApiModelProperty(value = "토큰 아이디")
  private Long tokenId;

  @ApiModelProperty(value = "멤버 아이디")
  private Long memberUid;

}
