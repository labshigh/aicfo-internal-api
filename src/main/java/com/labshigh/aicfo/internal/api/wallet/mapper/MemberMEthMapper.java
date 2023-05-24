package com.labshigh.aicfo.internal.api.wallet.mapper;

import com.labshigh.aicfo.internal.api.wallet.dao.MemberMEthDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MemberMEthMapper {

  void insertMemberMEth(MemberMEthDao memberMEthDao );
}
