package com.labshigh.aicfo.internal.api.member.mapper;

import com.labshigh.aicfo.internal.api.member.dao.MemberFileDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MemberFileMapper {

  void insert(MemberFileDao memberFileDao);
  MemberFileDao get(MemberFileDao memberFileDao);
}
