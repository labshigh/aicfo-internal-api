package com.labshigh.aicfo.internal.api.wallet.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberWalletAdminRequestModel {


  private String search;
  private SearchType searchType;


  public enum SearchType {
    EMAIL,
    ADDRESS

  }

}
