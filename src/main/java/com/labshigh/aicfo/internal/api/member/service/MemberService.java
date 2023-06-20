package com.labshigh.aicfo.internal.api.member.service;

import com.labshigh.aicfo.core.helper.CryptoHelper;
import com.labshigh.aicfo.core.models.ResponseListModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.common.utils.FileUploadUtils;
import com.labshigh.aicfo.internal.api.common.utils.MailUtils;
import com.labshigh.aicfo.internal.api.common.utils.SmsUtils;
import com.labshigh.aicfo.internal.api.common.utils.TelegramUtils;
import com.labshigh.aicfo.internal.api.member.dao.MemberDao;
import com.labshigh.aicfo.internal.api.member.dao.MemberLoginHistoryDao;
import com.labshigh.aicfo.internal.api.member.dao.SnsInfoDao;
import com.labshigh.aicfo.internal.api.member.mapper.MemberFileMapper;
import com.labshigh.aicfo.internal.api.member.mapper.MemberLoginHistoryMapper;
import com.labshigh.aicfo.internal.api.member.mapper.MemberMapper;
import com.labshigh.aicfo.internal.api.member.model.request.MemberInsertRequestModel;
import com.labshigh.aicfo.internal.api.member.model.request.MemberListRequestModel;
import com.labshigh.aicfo.internal.api.member.model.request.MemberSigninRequestModel;
import com.labshigh.aicfo.internal.api.member.model.response.MemberResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberMapper memberMapper;
  private final MemberFileMapper memberFileMapper;
  private final MemberLoginHistoryMapper memberLoginHistoryMapper;
  private final MailUtils mailUtils;
  private final SmsUtils smsUtils;
  private final FileUploadUtils fileUploadUtils;

  private final RedisTemplate redisTemplate;
  private final SpringTemplateEngine thymeleafTemplateEngine;
  @Value("${ncloud.mail-storage.email-verify-template-id}")
  private Integer emailVerifiedTemplateId;
  @Value("${ncloud.mail-storage.tokenExpirationTime}")
  private long tokenExpirationTime;
  @Value("${ncloud.sms-storage.from-phone-number}")
  private String fromPhoneNumber;
  @Value("${ncloud.sms-storage.expirationTime}")
  private long smsExpirationTime;
  private final TelegramUtils telegramUtils;


  public MemberResponseModel getByUid(long memberUid) {

    MemberDao memberDao = memberMapper.get(MemberDao.builder()
            .uid(memberUid)
            .build());

    if (memberDao == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }
    return convertMemberResponseModel(memberDao);
  }

  public MemberResponseModel getSocialMemberInfo(String email) {

    MemberDao memberDao;
    memberDao = memberMapper.getSocialMemberInfo(MemberDao.builder()
            .email(email)
            .build());

    if (memberDao == null) {
      memberDao = new MemberDao();
//      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    } else if(memberDao.getSnsType().equals("none")) {
      throw new ServiceException(Constants.MSG_DUPLICATE_ACCOUNT);
    }
    return convertMemberResponseModel(memberDao);
  }

  @Transactional
  public MemberResponseModel insertByEmail(MemberInsertRequestModel memberInsertRequestModel) throws Exception  {

  // 압화 해제 및 암호화
    String descryptPassword = CryptoHelper.decrypt(memberInsertRequestModel.getPassword());

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    String password = passwordEncoder.encode(descryptPassword);

    SnsInfoDao snsInfo = new SnsInfoDao();
    snsInfo.setSnsName(memberInsertRequestModel.getSnsName());
    snsInfo.setSnsId(memberInsertRequestModel.getSnsId());
    snsInfo.setSnsType(memberInsertRequestModel.getSnsName());
    MemberDao memberDAO = MemberDao.builder()
            .email(memberInsertRequestModel.getEmail())
            .password(password)
            .phoneNumber(memberInsertRequestModel.getPhoneNumber())
            .phoneVerifiedFlag(memberInsertRequestModel.isPhoneVerifiedFlag())
            .termsOfUse(memberInsertRequestModel.isTermsOfUse())
            .privacyPolicy(memberInsertRequestModel.isPrivacyPolicy())
            .personalInfoUse(memberInsertRequestModel.isPersonalInfoUse())
            .userAgeVerification(memberInsertRequestModel.isUserAgeVerification())
            .emailVerifiedFlag(memberInsertRequestModel.isEmailVerifiedFlag())
            .phoneNumber(memberInsertRequestModel.getPhoneNumber())
            .snsType(memberInsertRequestModel.getSnsName())
            .usedFlag(false)
            .snsInfoDao(snsInfo)
            .build();

    // member main table insert
    long newMemberUid = memberMapper.insertByEmail(memberDAO);

    if(StringUtils.hasText(memberInsertRequestModel.getSnsName())) {
      snsInfo.setMemberUid(memberDAO.getUid());
      memberMapper.insertSnsInfo(snsInfo);
    }

    return convertMemberResponseModel(memberDAO);
  }

  public MemberResponseModel signin(MemberSigninRequestModel memberSigninRequestModel) {

    MemberDao memberDao = memberMapper.get(MemberDao.builder().email(memberSigninRequestModel.getEmail()).build());

    if (memberDao == null) {
      throw new ServiceException(Constants.MSG_WRONG_PASSWORD_ERROR);
    } else {

      String descryptPassword = CryptoHelper.decrypt(memberSigninRequestModel.getPassword());

      PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

      if (!passwordEncoder.matches(descryptPassword, memberDao.getPassword())) {
        throw new ServiceException(Constants.MSG_WRONG_PASSWORD_ERROR);
      }
//      memberMapper.updateLoginDt(memberDao);
//      memberLoginHistoryMapper.insert(MemberLoginHistoryDao.builder()
//              .ip(memberSigninRequestModel.getIp())
//              .memberUid(memberDao.getUid())
//              .build());
      return convertMemberResponseModel(memberDao);
    }
  }

  private MemberResponseModel convertMemberResponseModel(MemberDao dao) {
    return MemberResponseModel.builder()
            .uid(dao.getUid())
            .createdAt(dao.getCreatedAt())
            .updatedAt(dao.getUpdatedAt())
            .deletedFlag(dao.isDeletedFlag())
            .usedFlag(dao.isUsedFlag())
            .email(dao.getEmail())
            .emailVerifiedFlag(dao.isEmailVerifiedFlag())
            .phoneNumber(dao.getPhoneNumber())
            .phoneVerifiedFlag(dao.isPhoneVerifiedFlag())
            .termsOfUse(dao.isTermsOfUse())
            .privacyPolicy(dao.isPrivacyPolicy())
            .personalInfoUse(dao.isPersonalInfoUse())
            .userAgeVerification(dao.isUserAgeVerification())

            .build();
  }

}
