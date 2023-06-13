package com.labshigh.aicfo.internal.api.member.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class MemberDao {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  @JsonIgnore
  private String password;
  private boolean deletedFlag;
  private boolean usedFlag;
  private String nickname;
  private String email;
  private boolean emailVerifiedFlag;
  private String phoneNumber;
  private boolean phoneVerifiedFlag;
  private boolean emailNewsletterFlag;
  private boolean smsSendFlag;
  private boolean termsOfUse;
  private boolean privacyPolicy;
  private boolean personalInfoUse;
  private boolean userAgeVerification;
  private String snsType;
  private SnsInfoDao snsInfoDao;



}