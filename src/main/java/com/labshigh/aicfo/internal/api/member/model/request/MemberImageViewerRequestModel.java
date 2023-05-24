package com.labshigh.aicfo.internal.api.member.model.request;

import com.labshigh.aicfo.internal.api.common.utils.enums.FileType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberImageViewerRequestModel {

  private String email;
  private FileType fileType;
}
