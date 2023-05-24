package com.labshigh.aicfo.internal.api.wallet.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class WalletTransactionPutRequestModel {

  @ApiModelProperty(value = "userId")
  private String userId;
  @ApiModelProperty(value = "토큰 고유 번호")
  private Long tokenId;
  @ApiModelProperty(value = "코인 고유 번호")
  private Long coinId;
  @ApiModelProperty(value = "지급 타입 ( 서비스 정의 )")
  private String type;
  @ApiModelProperty(value = "지급 수량 (-가능")
  private Double amount;
  @ApiModelProperty(value = "지급 사유")
  private String memo;
  @ApiModelProperty(value = "수수료")
  private String fee;
  @ApiModelProperty(value = "Webhook Event 처리 여부")
  private boolean event;
  @ApiModelProperty(value = "송금 Lockup 여부")
  private Double lockAmount;
  @ApiModelProperty(value = "memberUid")
  private Long memberUid;

}
