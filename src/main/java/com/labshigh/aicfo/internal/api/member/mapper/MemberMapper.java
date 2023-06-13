package com.labshigh.aicfo.internal.api.member.mapper;

import com.labshigh.aicfo.internal.api.member.dao.MemberDao;
import com.labshigh.aicfo.internal.api.member.dao.SnsInfoDao;
import com.labshigh.aicfo.internal.api.member.model.request.MemberApporveRequestModel;
import com.labshigh.aicfo.internal.api.member.model.request.MemberListRequestModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MemberMapper {

  long insertByEmail(MemberDao memberDao);
  void insertSnsInfo(SnsInfoDao snsInfoDao);
//  void insertByMetamask(MemberDao memberDao);
  MemberDao get(MemberDao memberDao);

  void updateLoginDt(MemberDao memberDao);
  void updateLoginHistory(MemberDao memberDao);
  MemberDao getSocialMemberInfo(MemberDao memberDao);
}
