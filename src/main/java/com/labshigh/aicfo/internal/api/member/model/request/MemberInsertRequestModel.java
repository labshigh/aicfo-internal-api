package com.labshigh.aicfo.internal.api.member.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberInsertRequestModel {
  private String nickname;
  private String email;
  private String password;
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
  private String snsName;
  private String snsId;
}