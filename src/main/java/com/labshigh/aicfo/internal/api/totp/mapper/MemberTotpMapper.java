package com.labshigh.aicfo.internal.api.totp.mapper;

import com.labshigh.aicfo.internal.api.totp.dao.MemberTotpDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MemberTotpMapper {

  void insert(MemberTotpDAO memberTotpDAO);
  void activate(MemberTotpDAO memberTotpDAO);
  void delete(MemberTotpDAO memberTotpDAO);

  MemberTotpDAO detail(MemberTotpDAO memberTotpDAO);
}
