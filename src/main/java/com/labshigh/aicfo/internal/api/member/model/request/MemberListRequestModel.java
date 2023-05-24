package com.labshigh.aicfo.internal.api.member.model.request;

import com.labshigh.aicfo.core.models.RequestPageModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberListRequestModel extends RequestPageModel {
  private String email;
  private String searchKey;
  private String searchValue;

}
