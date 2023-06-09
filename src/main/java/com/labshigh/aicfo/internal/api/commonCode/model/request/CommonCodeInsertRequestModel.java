package com.labshigh.aicfo.internal.api.commonCode.model.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CommonCodeInsertRequestModel {

  private String name;
  private String nameKr;
  private MultipartFile image;
  private int sort;
  private boolean usedFlag;
  private Long parentCommonCodeUid;
}