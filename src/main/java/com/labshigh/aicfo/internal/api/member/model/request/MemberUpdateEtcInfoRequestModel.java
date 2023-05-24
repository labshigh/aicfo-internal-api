package com.labshigh.aicfo.internal.api.member.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateEtcInfoRequestModel {

  private long memberUid;
  private Boolean emailVerifiedFlag;
  private Boolean phoneVerifiedFlag;
  private boolean emailNewsletterFlag;


}
