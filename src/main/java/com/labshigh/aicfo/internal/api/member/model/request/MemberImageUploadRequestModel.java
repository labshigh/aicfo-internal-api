package com.labshigh.aicfo.internal.api.member.model.request;

import com.labshigh.aicfo.internal.api.common.utils.enums.FileType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MemberImageUploadRequestModel {

  private long memberUid;
  private String email;
  private FileType fileType;
  private MultipartFile file;
}
