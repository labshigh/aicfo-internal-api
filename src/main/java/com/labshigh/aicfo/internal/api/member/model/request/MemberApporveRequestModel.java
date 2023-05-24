package com.labshigh.aicfo.internal.api.member.model.request;

import com.labshigh.aicfo.internal.api.common.utils.enums.ApproveType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberApporveRequestModel {

  @ApiModelProperty(value = "사용자 아이디")
  private Long uid;

  @ApiModelProperty(value = "email")
  private String email;

  @ApiModelProperty(value = "approveType")
  private ApproveType approveType;

  @ApiModelProperty(value = "승인자 아이디")
  private Long approveId;

  private boolean usedFlag;
}
