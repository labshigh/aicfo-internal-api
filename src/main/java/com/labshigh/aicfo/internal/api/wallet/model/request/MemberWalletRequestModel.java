package com.labshigh.aicfo.internal.api.wallet.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberWalletRequestModel {


  @ApiModelProperty(value = "코인 아이디")
  private Long coinId;

  @ApiModelProperty(value = "토큰 아이디")
  private Long tokenId;

  @ApiModelProperty(value = "지갑 아이디")
  private String userId;

  @ApiModelProperty(value = "visible")
  private String visible;

  @ApiModelProperty(value = "visible")
  private String me;

  @ApiModelProperty(value = "멤버Uid")
  private Long memberUid;

}
