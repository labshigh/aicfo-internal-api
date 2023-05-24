package com.labshigh.aicfo.internal.api.member.mapper;

import com.labshigh.aicfo.internal.api.member.dao.MemberLoginHistoryDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MemberLoginHistoryMapper {

  void insert(MemberLoginHistoryDao memberLoginHistoryDao);
}
