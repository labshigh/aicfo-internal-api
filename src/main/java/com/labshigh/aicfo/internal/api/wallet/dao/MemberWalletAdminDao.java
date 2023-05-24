package com.labshigh.aicfo.internal.api.wallet.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberWalletAdminDao {

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

  private String userId;
  private String referrerCode;
  private String referrer;

  private BigDecimal migEth;
  private BigDecimal migStaking;
  private String email;
  private boolean emailVerifiedFlag;
  private String nickname;

}