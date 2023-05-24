package com.labshigh.aicfo.internal.api.member.model.request;

import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VerifyEmailModel {

  private long uid;
  private long verifyTime;
  private String email;
  private String code;
  private long walletId;
  private Constants.MailType mailType;
}
