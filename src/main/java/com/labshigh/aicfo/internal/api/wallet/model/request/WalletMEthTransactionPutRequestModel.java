package com.labshigh.aicfo.internal.api.wallet.model.request;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WalletMEthTransactionPutRequestModel {

  @ApiModelProperty(value = "mEth 지급 타입")
  private Long methType;
  @ApiModelProperty(value = "meth 사용 수량")
  private BigDecimal methUseAmount;
  @ApiModelProperty(value = "지급 사유")
  private String memo;
  @ApiModelProperty(value = "memberUid")
  private Long memberUid;
  @ApiModelProperty(value = "walletId")
  private Long walletId;
  private Long itemBuyUid;
  private Long itemUid;

}
