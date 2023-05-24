package com.labshigh.aicfo.internal.api.wallet.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberWalletAdminResponseModel {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private boolean usedFlag;
  private Long coinId;
  private Long tokenId;
  private String name;
  private String address;
  private Long accountId;
  private Long memberUid;

  private BigDecimal mEth;
  private BigDecimal balance;
  private String balanceStr;

  private String userId;
  private String referrerCode;
  private String referrer;

  private BigDecimal migEth;
  private BigDecimal migStaking;
  private String email;
  private boolean emailVerifiedFlag;
  private String nickname;
}
