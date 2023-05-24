package com.labshigh.aicfo.internal.api.member.model.response;

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
public class MemberResponseModel {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private boolean usedFlag;
  private String nickname;
  private String email;
  private boolean emailVerifiedFlag;
  private String phoneNumber;
  private boolean phoneVerifiedFlag;
  private boolean emailNewsletterFlag;
  private boolean otpFlag;
  private String nationalCode;
  private String referrer;
  private String referrerCode;
  private String walletAddress;
  private boolean juminFlag;
  private boolean codeFlag;
  private String code;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime codeDate;

  private String approveType;
  private Long approveId;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime approveDate;
  private BigDecimal mEth;
  private boolean migratedFlag;

  private Long walletId;
  private boolean imsiPasswordFlag;
  private String cryptobroId;
  private String migBalance;
}