package com.labshigh.aicfo.internal.api.member.model.response;

import com.labshigh.aicfo.internal.api.common.utils.enums.FileType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberFileResponseModel {
  private String email;
  private FileType fileType;
  private String file;
}