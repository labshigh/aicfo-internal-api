package com.labshigh.aicfo.internal.api.exchange.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExchangeListGetRequestModel {

  @ApiModelProperty(value = "exchangeNameList")
  private List<String> exchangeNameList;

}
