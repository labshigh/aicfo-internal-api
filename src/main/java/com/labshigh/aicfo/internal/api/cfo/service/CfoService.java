package com.labshigh.aicfo.internal.api.cfo.service;

import com.labshigh.aicfo.internal.api.cfo.dao.CfoDao;
import com.labshigh.aicfo.internal.api.cfo.mapper.CfoMapper;
import com.labshigh.aicfo.internal.api.cfo.model.request.CfoListRequestModel;
import com.labshigh.aicfo.internal.api.cfo.model.response.CfoResponseModel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CfoService {

  @Value("${ncloud.object-storage.end-point}")
  private String s3EndPoint;
  @Value("${ncloud.object-storage.bucket}")
  private String s3NftBucket;

  @Autowired
  private CfoMapper cfoMapper;

  public List<CfoResponseModel> listCfo(CfoListRequestModel requestModel) {
    return cfoMapper.list(requestModel).stream().map(this::convertCfoResponseModel)
        .collect(Collectors.toList());
  }

  private CfoResponseModel convertCfoResponseModel(CfoDao dao) {
    return CfoResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .name(dao.getName())
        .counselKindCommonCodeUid(dao.getCounselKindCommonCodeUid())
        .counselKindCommonCodeName(dao.getCounselKindCommonCodeName())
        .career(dao.getCareer())
        .profileUri(getFileUri(dao.getProfileUri()))
        .build();
  }

  private String getFileUri(String uri) {

    if (uri == null) {
      return null;
    }

    return "https://" + s3EndPoint + "/" + s3NftBucket + "/" + uri;
  }

}
