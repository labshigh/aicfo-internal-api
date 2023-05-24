package com.labshigh.aicfo.internal.api.abusing.mapper;

import com.labshigh.aicfo.internal.api.abusing.dao.AbusingDao;
import com.labshigh.aicfo.internal.api.abusing.model.request.AbusingRestRequestModel;
import com.labshigh.aicfo.internal.api.abusing.model.request.AbusingRequestModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AbusingMapper {
    int count(AbusingRequestModel abusingRequestModel);
    List<AbusingDao> list(AbusingRequestModel abusingRequestModel);
    List<AbusingDao> abusingMemoList(long uid);
    int detailMemberCount(AbusingRequestModel abusingRequestModel);
    List<AbusingDao> detailMember(AbusingRequestModel abusingRequestModel);
    void insert(AbusingRestRequestModel abusingRestRequestModel);
    void updateMember(AbusingRestRequestModel abusingRestRequestModel);
}
