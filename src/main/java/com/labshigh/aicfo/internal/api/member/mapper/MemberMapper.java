package com.labshigh.aicfo.internal.api.member.mapper;

import com.labshigh.aicfo.internal.api.member.dao.MemberDao;
import com.labshigh.aicfo.internal.api.member.model.request.MemberApporveRequestModel;
import com.labshigh.aicfo.internal.api.member.model.request.MemberListRequestModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MemberMapper {

  void insertByEmail(MemberDao memberDao);
  void insertByMetamask(MemberDao memberDao);

  void updateEmail(MemberDao memberDao);

  void updateWallet(MemberDao memberDao);

  void updatePhoneNumber(MemberDao memberDao);
  void updateEtcInfo(MemberDao memberDao);

  void updateJuminFileId(MemberDao memberDao);

  void updateCodeFileId(MemberDao memberDao);
  void updateAuthFlag(MemberDao memberDao);
  void updateCode(MemberDao memberDao);

  int count(MemberListRequestModel requestModel);
  List<MemberDao> list(MemberListRequestModel requestModel);
  void updateAuth(MemberApporveRequestModel requestModel);
  MemberDao get(MemberDao memberDao);

  String getByEmail(String email);

  String getByNickname(String nickname);

  void updatePassword(MemberDao adminDAO);

  void updateNickname(MemberDao memberDao);
  void updateReferrer(MemberDao memberDao);

  void updateReferrerCode(MemberDao memberDao);

  void updateLoginDt(MemberDao memberDao);
  void updateLoginHistory(MemberDao memberDao);

  // 이관 체크용
  int migCount(MemberListRequestModel requestModel);
  List<MemberDao> migList(MemberListRequestModel requestModel);
}
