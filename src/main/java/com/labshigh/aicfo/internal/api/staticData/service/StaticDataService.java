package com.labshigh.aicfo.internal.api.staticData.service;


import com.google.gson.reflect.TypeToken;
import com.labshigh.aicfo.core.utils.JsonUtils;
import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.staticData.dao.StaticDataDao;
import com.labshigh.aicfo.internal.api.staticData.mapper.StaticDataMapper;
import com.labshigh.aicfo.internal.api.staticData.model.request.StaticDataDetailRequestModel;
import com.labshigh.aicfo.internal.api.staticData.model.response.StaticDataResponseModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class StaticDataService {

  @Autowired
  StaticDataMapper staticDataMapper;

  public StaticDataResponseModel detail(StaticDataDetailRequestModel requestModel) {

    StaticDataResponseModel result = this.convertStaticDataResponseModel(
        staticDataMapper.detail(requestModel.getStaticDataUid()));

    if (!StringUtils.isEmpty(requestModel.getOrderName())) {
      result.setDataJson(orderByJsonData(result.getDataJson(), requestModel.getOrderName()));
    }
    return result;
  }

  private List<HashMap<String, Object>> orderByJsonData(List<HashMap<String, Object>> dataJsonList,
      String orderName) {

    return dataJsonList.stream().sorted(Comparator.comparing(v -> v.get(orderName).toString()))
        .collect(Collectors.toList());

  }


  private StaticDataResponseModel convertStaticDataResponseModel(StaticDataDao dao) {

    List<HashMap<String, Object>> dataJsonList = JsonUtils.convertJsonStringToMap(
        dao.getDataJson(),
        new TypeToken<ArrayList<HashMap<String, Object>>>() {
        }.getType());

    return StaticDataResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.isUsedFlag())
        .name(dao.getName())
        .dataJson(dataJsonList)
        .build();
  }

}