package com.labshigh.aicfo.internal.api.wallet.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
public class MemberWalletWithdrawalResponseModel {
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private boolean usedFlag;
  private Long uid;
  private String name;
  private String address;
  private Long coinId;
  private Long tokenId;
  private Long memberUid;
  private String qrCode;

  private boolean internalWalletFlag;
  private String email;
  private String referrerCode;

  private MemberWalletWithdrawalHistory history;



}
