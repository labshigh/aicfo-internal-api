package com.labshigh.aicfo.internal.api.file.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileUploadResponseModel {

  private String fileUri;
  private String fileName;
}
