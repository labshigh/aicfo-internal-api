package com.labshigh.aicfo.internal.api.wallet.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WalletTransactionWaitRequestModel {

  @ApiModelProperty(value = "toAddress")
  private String toAddress;
  @ApiModelProperty(value = "받는 사람 아이디 ( 서비스 사용자로 존재 하는 경우 )")
  private String toUserId;
  @ApiModelProperty(value = "보내는 지갑의 코인 아이디")
  private Long coinId;
  @ApiModelProperty(value = "보내는 지갑의 토큰 아이디")
  private Long tokenId;
  @ApiModelProperty(value = "보내는 지갑의 사용자 아이디")
  private String userId;
  @ApiModelProperty(value = "거래 메모")
  private String memo;
  @ApiModelProperty(value = "보내는 수량")
  private Double amount;
  @ApiModelProperty(value = "관련 거래가 존재 하면 관련된 거래의 아이디를 저장")
  private Long refTransactionId;
  @ApiModelProperty(value = "서비스 정의 보내는 거래 타입 지정")
  private String sendType;
  @ApiModelProperty(value = "서비스 정의 받는 거래 타입 지정")
  private String receiveType;
  @ApiModelProperty(value = "수수료")
  private String fee;
  @ApiModelProperty(value = "USD 고정 출금요청 금액")
  private String usd;
  @ApiModelProperty(value = "기타내용")
  private String extraValue;
  @ApiModelProperty(value = "락업 설정 기간")
  private String lockUpDay;
  @ApiModelProperty(value = "락업 설정 수량")
  private String lockUpAmount;
  @ApiModelProperty(value = "memberUid")
  private Long memberUid;

}
