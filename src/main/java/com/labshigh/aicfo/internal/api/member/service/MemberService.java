package com.labshigh.aicfo.internal.api.member.service;

import com.labshigh.aicfo.core.helper.CryptoHelper;
import com.labshigh.aicfo.core.models.ResponseListModel;
import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.core.utils.JsonUtils;
import com.labshigh.aicfo.core.utils.MediaUtil;
import com.labshigh.aicfo.core.utils.RandomUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.common.utils.FileUploadUtils;
import com.labshigh.aicfo.internal.api.common.utils.MailUtils;
import com.labshigh.aicfo.internal.api.common.utils.SmsUtils;
import com.labshigh.aicfo.internal.api.common.utils.TelegramUtils;
import com.labshigh.aicfo.internal.api.common.utils.enums.ApproveType;
import com.labshigh.aicfo.internal.api.common.utils.enums.FileType;
import com.labshigh.aicfo.internal.api.common.utils.models.MailReceiveInfo;
import com.labshigh.aicfo.internal.api.common.utils.models.SmsMessage;
import com.labshigh.aicfo.internal.api.common.utils.models.SmsRequestModel;
import com.labshigh.aicfo.internal.api.file.model.response.FileUploadResponseModel;
import com.labshigh.aicfo.internal.api.member.dao.MemberDao;
import com.labshigh.aicfo.internal.api.member.dao.MemberFileDao;
import com.labshigh.aicfo.internal.api.member.dao.MemberLoginHistoryDao;
import com.labshigh.aicfo.internal.api.member.mapper.MemberFileMapper;
import com.labshigh.aicfo.internal.api.member.mapper.MemberLoginHistoryMapper;
import com.labshigh.aicfo.internal.api.member.mapper.MemberMapper;
import com.labshigh.aicfo.internal.api.member.model.request.*;
import com.labshigh.aicfo.internal.api.member.model.response.MemberCodeResponseModel;
import com.labshigh.aicfo.internal.api.member.model.response.MemberFileResponseModel;
import com.labshigh.aicfo.internal.api.member.model.response.MemberResponseModel;
import com.labshigh.aicfo.internal.api.wallet.dao.MemberWalletDao;
import com.labshigh.aicfo.internal.api.wallet.mapper.MemberWalletMapper;
import com.labshigh.aicfo.internal.api.wallet.service.MemberWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.naming.AuthenticationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.SignatureException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberWalletService walletService;
  private final MemberMapper memberMapper;
  private final MemberFileMapper memberFileMapper;
  private final MemberLoginHistoryMapper memberLoginHistoryMapper;
  private final MemberWalletMapper memberWalletMapper;
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

  public ResponseListModel list(MemberListRequestModel requestModel) {
    ResponseListModel result = new ResponseListModel();

    int totalCount = memberMapper.count(requestModel);

    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;
    }

    List<MemberResponseModel> memberDaoList = memberMapper.list(requestModel).stream()
      .map(this::convertMemberResponseModel).collect(Collectors.toList());

    result.setList(memberDaoList);

    return result;
  }
  @Transactional
  public MemberResponseModel insertByEmail(MemberInsertRequestModel memberInsertRequestModel) throws Exception  {


    String email = memberMapper.getByEmail(
      memberInsertRequestModel.getEmail());

    if (email != null) {
      throw new ServiceException(String.format(Constants.MSG_DUPLICATED_DATA));
    }
    if(null != memberInsertRequestModel.getReferrer()){
      // 추천인 코드 확인
      MemberWalletDao referrerDao =  memberWalletMapper.get(MemberWalletDao.builder()
        .referrerCode(memberInsertRequestModel.getReferrer())
        .build());

      if (referrerDao == null) {
        throw new ServiceException(Constants.MSG_MEMBER_REFFERER_NO);
      }

    }

    // 압화 해제 및 암호화
    String descryptPassword = CryptoHelper.decrypt(memberInsertRequestModel.getPassword());

    if (!checkRegexPassword(descryptPassword)) {
      throw new ServiceException(Constants.MSG_NOT_MATCH_PASSWORD);
    }
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    String password = passwordEncoder.encode(descryptPassword);
    MemberDao memberDAO = MemberDao.builder()
      .email(memberInsertRequestModel.getEmail())
      .password(password)
      .referrer(memberInsertRequestModel.getReferrer())
      .referrerCode(RandomUtils.randomReferrerCode(6))
      .emailVerifiedFlag(false)
      .juminFlag(true)
      .codeFlag(true)
      .usedFlag(false)
      .build();

    // member main table insert
    memberMapper.insertByEmail(memberDAO);
    // member 로그인 정보 table insert
    log.debug("memberInsertRequestModel : {}",
      JsonUtils.convertObjectToJsonString(memberInsertRequestModel));

    String randomRefererCode = RandomUtils.randomReferrerCode(6);
    MemberWalletDao randomRefererCodeDao =  memberWalletMapper.get(MemberWalletDao.builder()
      .referrerCode(memberInsertRequestModel.getReferrer())
      .build());
    while (randomRefererCodeDao != null) {
      randomRefererCode = RandomUtils.randomReferrerCode(6);
      randomRefererCodeDao =  memberWalletMapper.get(MemberWalletDao.builder()
        .referrerCode(randomRefererCode)
        .build());
    }

    MemberWalletDao memberWalletDao = MemberWalletDao.builder()
      .memberUid(memberDAO.getUid())
      .tokenId(2l)
      .coinId(2l)
      .referrer(memberDAO.getReferrer())
      .referrerCode(randomRefererCode)
      .build();
    // 멤버 월렛 추가.
    memberWalletMapper.insertMemberWallet(memberWalletDao);
    memberDAO.setReferrerCode(randomRefererCode);
    memberDAO.setWalletId(memberWalletDao.getUid());
    memberMapper.updateWallet(memberDAO);

    // 이메일 인증
    MemberSendVerifyEmailRequestModel memberSendVerifyEmailRequestModel = new MemberSendVerifyEmailRequestModel();
    memberSendVerifyEmailRequestModel.setMemberUid(memberDAO.getUid());
    memberSendVerifyEmailRequestModel.setEmail(memberDAO.getEmail());
    sendVerifyEmail(memberSendVerifyEmailRequestModel);

    telegramUtils.postSendOnAdminTracking("Application for new member : "+memberDAO.getEmail());

    return convertMemberResponseModel(memberDAO);
  }
  @Transactional
  public MemberResponseModel insertMetaMask(MemberInsertMetaMaskRequestModel requestModel)
      throws SignatureException, AuthenticationException {

    // Wallet Address 가져오기
    String walletAddress = requestModel.getPublicKey().trim();

    log.debug("Wallet Address : {}", walletAddress);

    MemberDao memberDao = memberMapper.get(MemberDao.builder()
        .walletAddress(walletAddress)
        .build());

    // 해당 wallet address 로 가입이 된 내역이 없을 경우
    if (memberDao == null) {

      // member main table insert
      MemberDao memberDaoInsert = MemberDao.builder().walletAddress(walletAddress).build();

      memberMapper.insertByMetamask(memberDaoInsert);

      MemberDao memberDaoGet = memberMapper.get(MemberDao.builder()
          .uid(memberDaoInsert.getUid())
          .build());

      return convertMemberResponseModel(memberDaoGet);
    } else {

      return convertMemberResponseModel(memberDao);
    }
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
      memberMapper.updateLoginDt(memberDao);
      memberLoginHistoryMapper.insert(MemberLoginHistoryDao.builder()
          .ip(memberSigninRequestModel.getIp())
          .memberUid(memberDao.getUid())
        .build());
      return convertMemberResponseModel(memberDao);
    }
  }

  public MemberResponseModel signinWallet(SigninWalletRequestModel requestModel) {

    MemberWalletDao memberWalletDao = memberWalletMapper.get(MemberWalletDao.builder().referrerCode(requestModel.getReferrerCode())
      .memberUid(requestModel.getMemberUid()).build());


    if (memberWalletDao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_WALLET_NO);
    }

    MemberDao dao = memberMapper.get(MemberDao.builder().uid(requestModel.getMemberUid()).build());

    return MemberResponseModel.builder()
      .uid(dao.getUid())
      .createdAt(dao.getCreatedAt())
      .updatedAt(dao.getUpdatedAt())
      .deletedFlag(dao.isDeletedFlag())
      .usedFlag(dao.isUsedFlag())
      .walletAddress(dao.getWalletAddress())
      .nickname(dao.getNickname())
      .email(dao.getEmail())
      .emailVerifiedFlag(dao.isEmailVerifiedFlag())
      .phoneNumber(dao.getPhoneNumber())
      .phoneVerifiedFlag(dao.isPhoneVerifiedFlag())
      .emailNewsletterFlag(dao.isEmailNewsletterFlag())
      .nationalCode(dao.getNationalCode())
      .referrer(memberWalletDao.getReferrer())
      .referrerCode(memberWalletDao.getReferrerCode())
      .otpFlag(dao.isOtpFlag())
      .juminFlag(dao.isJuminFlag())
      .codeFlag(dao.isCodeFlag())
      .code(dao.getCode())
      .codeDate(dao.getCodeDate())
      .approveType(dao.getApproveType())
      .approveId(dao.getApproveId())
      .approveDate(dao.getApproveDate())
      .walletId(memberWalletDao.getUid())
      .build();

  }

  public MemberResponseModel getByUid(long memberUid,long walletId) {

    MemberDao memberDao = memberMapper.get(MemberDao.builder()
        .uid(memberUid)
        .build());

    MemberWalletDao memberWalletDao = memberWalletMapper.get(MemberWalletDao.builder().uid(walletId)
      .memberUid(memberUid).build());


    if (memberWalletDao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_WALLET_NO);
    }
    if (memberDao == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }
    memberDao.setReferrerCode(memberDao.getReferrerCode());
    memberDao.setWalletId(memberWalletDao.getUid());
    memberDao.setReferrer(memberWalletDao.getReferrer());
    return convertMemberResponseModel(memberDao);
  }

  public MemberResponseModel getByWalletAddress(MemberGetByWalletAddressRequestModel requestModel) {

    MemberDao memberDao = memberMapper.get(MemberDao.builder()
        .walletAddress(requestModel.getWalletAddress())
        .build());

    if (memberDao == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    return convertMemberResponseModel(memberDao);
  }

  @Transactional
  public ResponseModel sendVerifyEmail(MemberSendVerifyEmailRequestModel requestModel) throws Exception{

    MemberDao dao = memberMapper.get(MemberDao.builder()
        .email(requestModel.getEmail())
        .build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }

    if (dao.getEmail() != null && dao.getEmail().equals(requestModel.getEmail())
        && dao.isEmailVerifiedFlag()) {
      throw new ServiceException(Constants.MSG_ALREADY_VERIFIED_EMAIL);
    }

    HashMap<String, String> customMap = new HashMap<>();

    dao.setEmail(requestModel.getEmail());

    memberMapper.updateEmail(dao);

    return sendVerifyEmail(dao, Constants.MailType.JOIN, customMap);

  }

  private ResponseModel sendVerifyEmail(MemberDao model, Constants.MailType mailType,
      HashMap<String, String> customMap) throws Exception{

    //토큰 생성
    VerifyEmailModel tokenModel = VerifyEmailModel.builder()
        .uid(model.getUid())
        .email(model.getEmail())
        .verifyTime(
            Timestamp.valueOf(LocalDateTime.now().plusMinutes(tokenExpirationTime)).getTime())
        .mailType(mailType)
        .build();

    String tokenJson = CryptoHelper.encrypt(JsonUtils.convertObjectToJsonString(tokenModel));
    MailReceiveInfo mailReceiveInfo = MailReceiveInfo.builder()
        .address(model.getEmail())
        //.name(model.getName())
        .type("R")
        .parameters(customMap)
        .build();

    List<MailReceiveInfo> receiveInfoList = new ArrayList<>();
    receiveInfoList.add(mailReceiveInfo);
    ResponseModel result = null;
    Context thymeleafContext = new Context();
    Map<String, Object> templateModel = new HashMap<>();
    if(Constants.MailType.JOIN.equals(mailType)) {
      templateModel.put("url", "https://api.aicfo.vip/member/verifyEmail/" + tokenJson);
      thymeleafContext.setVariables(templateModel);
      String contents = thymeleafTemplateEngine.process("mail-templates/join", thymeleafContext);

      result = mailUtils.awsSESSend("Martini Pool 이메일 점유인증 링크", contents, model.getEmail());
      log.debug("mailSendResult : {}", JsonUtils.convertObjectToJsonString(result));
    }else if(Constants.MailType.FIND_PASSWORD.equals(mailType) ){
      templateModel.put("url", "https://aicfo.vip/auth/resetPassword/" + tokenJson);
      thymeleafContext.setVariables(templateModel);
      String contents = thymeleafTemplateEngine.process("mail-templates/find-password", thymeleafContext);

      result = mailUtils.awsSESSend("Martini Pool 패스워드 찾기", contents, model.getEmail());
      log.debug("mailSendResult : {}", JsonUtils.convertObjectToJsonString(result));
    }else if( Constants.MailType.FIND_PASSWORD_IMSI.equals(mailType)){
      templateModel.put("email", customMap.get("email"));
      templateModel.put("password", customMap.get("password"));
      thymeleafContext.setVariables(templateModel);
      String contents = thymeleafTemplateEngine.process("mail-templates/find-password-imsi", thymeleafContext);

      result = mailUtils.awsSESSend("Crypto BROS 회원 임시 비밀번호 발급", contents, model.getEmail());
      log.debug("mailSendResult : {}", JsonUtils.convertObjectToJsonString(result));
    }
    return result;
  }

  @Transactional
  public void verifyEmail(String token) {

    MemberDao dao = this.checkToken(token, Constants.MailType.JOIN);

    if (dao.isEmailVerifiedFlag()) {
      throw new ServiceException(Constants.MSG_ALREADY_VERIFIED_EMAIL);
    }

    dao.setEmailVerifiedFlag(true);

    memberMapper.updateEmail(dao);

  }

  @Transactional
  public void sendVerifySms(MemberSendSmsVerifyRequestModel requestModel) {
    MemberDao dao = memberMapper.get(
        MemberDao.builder().uid(requestModel.getMemberUid()).build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }
    if (dao.getPhoneNumber() != null) {
      if (dao.getNationalCode().equals(requestModel.getNationalCode())
          && dao.getPhoneNumber().equals(requestModel.getPhoneNumber())
          && dao.isPhoneVerifiedFlag()) {
        throw new ServiceException(Constants.MSG_ALREADY_VERIFIED_SMS);
      }
    }
    dao.setPhoneNumber(requestModel.getPhoneNumber());
    dao.setNationalCode(requestModel.getNationalCode());

    memberMapper.updatePhoneNumber(dao);

    String verifyCode = RandomStringUtils.randomNumeric(6);

    String smsContent = String.format(Constants.MSG_VERIFY_CONTENT_SMS, verifyCode);

    List<SmsMessage> smsMessages = new ArrayList<>();
    smsMessages.add(SmsMessage.builder()
        .to(requestModel.getPhoneNumber())
        .content(smsContent)
        .build());

    SmsRequestModel smsRequestModel = SmsRequestModel.builder()
        .type("LMS")
        .contentType("COMM")
        .countryCode(requestModel.getNationalCode())
        .from(fromPhoneNumber)
        .content(smsContent)
        .messages(smsMessages)
        .build();
    smsUtils.send(smsRequestModel);

    redisTemplate.opsForValue()
      .set("aicfoVerifyPhonNumber:" + requestModel.getNationalCode()
          + requestModel.getPhoneNumber(),
        verifyCode, smsExpirationTime, TimeUnit.SECONDS);
  }

  @Transactional
  public void verifySms(MemberVerifySmsRequestModel requestModel) {

    MemberDao dao = memberMapper.get(MemberDao.builder().uid(requestModel.getMemberUid()).build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }

    if (!dao.getNationalCode().equals(requestModel.getNationalCode())
        || !dao.getPhoneNumber().equals(requestModel.getPhoneNumber())) {
      throw new ServiceException(Constants.MSG_VALUE_ERROR);
    }

    String rediskey = "aicfoVerifyPhonNumber:" + requestModel.getNationalCode()
        + requestModel.getPhoneNumber();

    // TODO : verifiedCode 로 인증번호 체크
    Object verifiedCodeObj = redisTemplate.opsForValue().get(rediskey);

    if (verifiedCodeObj == null || !requestModel.getVerifyCode()
        .equals(verifiedCodeObj.toString())) {
      throw new ServiceException(Constants.MSG_TOKEN_ERROR);
    }

    dao.setPhoneVerifiedFlag(true);
    memberMapper.updatePhoneNumber(dao);

    redisTemplate.delete(rediskey);
  }

  public MemberResponseModel updateEtcInfo(MemberUpdateEtcInfoRequestModel requestModel) {

    MemberDao dao = memberMapper.get(MemberDao.builder()
        .uid(requestModel.getMemberUid())
        .build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }

    if (requestModel.getPhoneVerifiedFlag() != null && Boolean.FALSE.equals(
        requestModel.getPhoneVerifiedFlag())) {
      dao.setPhoneVerifiedFlag(false);
      dao.setPhoneNumber(null);
      dao.setNationalCode(null);
    }

    if (requestModel.getEmailVerifiedFlag() != null && Boolean.FALSE.equals(
        requestModel.getEmailVerifiedFlag())) {
      dao.setEmailVerifiedFlag(requestModel.getEmailVerifiedFlag());
      dao.setEmail(null);
    }

    dao.setEmailNewsletterFlag(requestModel.isEmailNewsletterFlag());

    memberMapper.updateEtcInfo(dao);

    return convertMemberResponseModel(dao);

  }

  public MemberCodeResponseModel sendCode(MemberUpdateCodeRequestModel requestModel) {

    MemberDao dao = memberMapper.get(MemberDao.builder()
      .email(requestModel.getEmail())
      .build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }

    Date nowDate = new Date();
    SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("MMdd");
    SimpleDateFormat timeSimpleDateFormat = new SimpleDateFormat("HHmm");

    String dateStr = dateSimpleDateFormat.format(nowDate);
    String timeStr = timeSimpleDateFormat.format(nowDate);

    String code = dateStr+ " " +timeStr+ " " + RandomUtils.randomCodeNum(6);

    dao.setCode(code);

    memberMapper.updateCode(dao);

    return MemberCodeResponseModel.builder()
      .email(requestModel.getEmail())
      .code(code)
      .build();

  }

  public MemberResponseModel updateNickname(MemberUpdateNicknameRequestModel requestModel) {

    MemberDao dao = memberMapper.get(MemberDao.builder()
      .uid(requestModel.getMemberUid())
      .build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }

    dao.setNickname(requestModel.getNickname());

    memberMapper.updateNickname(dao);

    return convertMemberResponseModel(dao);

  }

  public MemberResponseModel updateReferrer(MemberUpdateReferrerRequestModel requestModel) {

    MemberDao dao = memberMapper.get(MemberDao.builder()
      .uid(requestModel.getMemberUid())
      .build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }

    MemberWalletDao referrerDao =  memberWalletMapper.get(MemberWalletDao.builder()
      .referrerCode(requestModel.getReferrer())
      .build());

    if (referrerDao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_REFFERER_NO);
    }
    // 본인 추천인 코드 입력 x
    MemberWalletDao referrerDaoList =  memberWalletMapper.get(MemberWalletDao.builder()
      .referrerCode(requestModel.getReferrer())
      .memberUid(requestModel.getMemberUid())
      .build());
    if (referrerDaoList != null) {
      throw new ServiceException(Constants.MSG_MEMBER_REFFERER_USER_NO);
    }

    MemberWalletDao memberWalletDao =  memberWalletMapper.get(MemberWalletDao.builder()
      .memberUid(requestModel.getMemberUid())
      .uid(requestModel.getWalletId())
      .build());

    memberWalletDao.setReferrer(requestModel.getReferrer());
    memberWalletMapper.updateReferrer(memberWalletDao);

    dao.setReferrer(requestModel.getReferrer());

    return convertMemberResponseModel(dao);

  }

  public MemberResponseModel updateReferrerCode(MemberUpdateReferrerRequestModel requestModel) {

    MemberDao dao = memberMapper.get(MemberDao.builder()
      .uid(requestModel.getMemberUid())
      .build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }
    if( dao.getReferrerCode() != null ){
      throw new ServiceException(Constants.MSG_MEMBER_ALREADY_REFFERER_NO);
    }
    String randomRefererCode = RandomUtils.randomReferrerCode(6);

    MemberWalletDao referrerDao =  memberWalletMapper.get(MemberWalletDao.builder()
      .referrerCode(randomRefererCode)
      .build());
    while (referrerDao != null) {
      randomRefererCode = RandomUtils.randomReferrerCode(6);
       referrerDao =  memberWalletMapper.get(MemberWalletDao.builder()
        .referrerCode(randomRefererCode)
        .build());
    }

    dao.setReferrerCode(randomRefererCode);

    memberMapper.updateReferrerCode(dao);

    return convertMemberResponseModel(dao);

  }
  public void updatePassword(MemberUpdatePasswordRequestModel requestModel) {

    MemberDao dao = memberMapper.get(MemberDao.builder()
      .uid(requestModel.getMemberUid())
      .build());

    if(dao == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    String descryptCurrentPassword = CryptoHelper.decrypt(requestModel.getCurrentPassword());

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    if(!passwordEncoder.matches(descryptCurrentPassword, dao.getPassword())) {
      throw new ServiceException(String.format(Constants.MSG_WRONG_PASSWORD_ERROR));
    }

    String descryptPassword = CryptoHelper.decrypt(requestModel.getPassword());

    String password = passwordEncoder.encode(descryptPassword);

    memberMapper.updatePassword(dao.builder()
      .uid(requestModel.getMemberUid())
      .password(password)
      .imsiPasswordFlag(false)
      .build());
  }

  @Transactional
  public ResponseModel sendEmailFindPassword(MemberSendEmaiFindPasswordlRequestModel requestModel) throws Exception{

    MemberDao dao = memberMapper.get(MemberDao.builder()
      .email(requestModel.getEmail())
      .build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }

    HashMap<String, String> customMap = new HashMap<>();

    return sendVerifyEmail(dao, Constants.MailType.FIND_PASSWORD, customMap);

  }


  public MemberResponseModel findIdCryptobros(String id) throws Exception{

    MemberDao dao = memberMapper.get(MemberDao.builder()
      .cryptobroId(id)
      .build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }
    if ( !dao.isMigratedFlag() ) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }

    return convertMemberResponseModel(dao);

  }

  @Transactional
  public ResponseModel sendEmailFindPasswordImsi(MemberSendEmaiFindPasswordlRequestModel requestModel) throws Exception{

    MemberDao dao = memberMapper.get(MemberDao.builder()
      .email(requestModel.getEmail())
      .build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }
    if ( !dao.isMigratedFlag() ) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }

    String password = RandomUtils.randomPassword(12);
    HashMap<String, String> customMap = new HashMap<>();
    customMap.put("email",dao.getEmail());
    customMap.put("password",password);
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    String encodePassword = passwordEncoder.encode(password);

    memberMapper.updatePassword(dao.builder()
      .uid(dao.getUid())
      .password(encodePassword)
      .imsiPasswordFlag(true)
      .build());

    return sendVerifyEmail(dao, Constants.MailType.FIND_PASSWORD_IMSI, customMap);

  }

  @Transactional
  public ResponseModel checkTo(MemberSendEmaiFindPasswordlRequestModel requestModel) throws Exception{

    MemberDao dao = memberMapper.get(MemberDao.builder()
      .email(requestModel.getEmail())
      .build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }

    HashMap<String, String> customMap = new HashMap<>();

    return sendVerifyEmail(dao, Constants.MailType.FIND_PASSWORD, customMap);

  }
  public void updatePasswordWithToken(MemberUpdatePasswordRequestModel requestModel) {


    MemberDao dao = this.checkToken(requestModel.getToken(), Constants.MailType.FIND_PASSWORD);

    dao = memberMapper.get(MemberDao.builder()
      .uid(dao.getUid())
      .build());

    if(dao == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    String descryptPassword = CryptoHelper.decrypt(requestModel.getPassword());

    String password = passwordEncoder.encode(descryptPassword);

    memberMapper.updatePassword(dao.builder()
      .uid(dao.getUid())
      .password(password)
      .imsiPasswordFlag(false)
      .build());
  }
  public MemberResponseModel approve(MemberApporveRequestModel requestModel) {

    MemberDao dao = memberMapper.get(MemberDao.builder()
      .uid(requestModel.getUid())
      .build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }
//    String code = RandomUtils.randomCodeNum(4) + " " +RandomUtils.randomCodeNum(4)+ " " + RandomUtils.randomCodeNum(6);

//    dao.setCode(code);
    if(ApproveType.approval.name().equals(requestModel.getApproveType().name())){
      requestModel.setUsedFlag(true);
    }else{
      requestModel.setUsedFlag(false);
    }
    memberMapper.updateAuth(requestModel);

    return convertMemberResponseModel(dao);

  }


  public MemberDao checkToken(String token, Constants.MailType mailType) {
    VerifyEmailModel verifyEmailModel = JsonUtils.convertJsonStringToObject(
        CryptoHelper.decrypt(token), VerifyEmailModel.class);
    log.debug("verifyEmailModel : {}", JsonUtils.convertObjectToJsonString(verifyEmailModel));

    if (verifyEmailModel == null || !verifyEmailModel.getMailType().equals(mailType)) {
      throw new ServiceException(Constants.MSG_TOKEN_ERROR);
    }

    long curTimeStamp = new Date().getTime();

    log.debug("curTimeStamp : {}", curTimeStamp);
    if (curTimeStamp > verifyEmailModel.getVerifyTime()) {
      throw new ServiceException(Constants.MSG_TOKEN_ERROR);
    }

    MemberDao dao = memberMapper.get(MemberDao.builder().uid(verifyEmailModel.getUid()).build());
    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }

    return dao;
  }

  public FileUploadResponseModel upload(MemberImageUploadRequestModel requestModel) throws IOException {

    String mediaName = requestModel.getFile().getOriginalFilename();
    String extension = FilenameUtils.getExtension(mediaName);

    if(!MediaUtil.acceptable(extension)) {
      throw new ServiceException(Constants.MSG_NOT_ACCEPTABLE_FILE);
    }

    MemberDao memberDao = memberMapper.get(MemberDao.builder().email(requestModel.getEmail()).build());
    if(memberDao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }
    String pathName = UUID.randomUUID().toString().replace("-", "");

    String objectName = pathName + "." + requestModel.getFile().getOriginalFilename()
      .substring(requestModel.getFile().getOriginalFilename().lastIndexOf(".") + 1);

    File tmpFile = new File("/tmp/" + objectName);
    Files.copy(requestModel.getFile().getInputStream(), tmpFile.toPath(),
      StandardCopyOption.REPLACE_EXISTING);


    byte[] fileContent = FileUtils.readFileToByteArray(tmpFile);
    String encodedString ="data:image/png;base64,"+ Base64.getEncoder().encodeToString(fileContent);
    MemberFileDao memberFileDao = MemberFileDao.builder()
      .memberUid(memberDao.getUid())
      .file(encodedString)
      .contentType(requestModel.getFile().getContentType())
      .build();

    memberFileMapper.insert(memberFileDao);

    if(FileType.member_code.equals(requestModel.getFileType())){
      memberDao.setCodeFileId(memberFileDao.getUid());
      memberMapper.updateCodeFileId(memberDao);
    }else if(FileType.member_jumin.equals(requestModel.getFileType())){
      memberDao.setJuminFileId(memberFileDao.getUid());
      memberMapper.updateJuminFileId(memberDao);
    }

    return FileUploadResponseModel.builder()
      .fileUri("ok")
      .build();
  }

  public MemberFileResponseModel fileViewer(MemberImageViewerRequestModel requestModel) throws IOException {

    MemberDao memberDao = memberMapper.get(MemberDao.builder().email(requestModel.getEmail()).build());
    Long uid = 0l;
    if( requestModel.getFileType().name().equals(FileType.member_code.name())){
        uid = memberDao.getCodeFileId();
    }else{
      uid = memberDao.getJuminFileId();
    }
    MemberFileDao memberFileDao = MemberFileDao.builder()
      .uid(uid)
      .build();

    memberFileDao = memberFileMapper.get(memberFileDao);

    return MemberFileResponseModel.builder().file(memberFileDao.getFile()).build();

  }

  private MemberResponseModel convertMemberResponseModel(MemberDao dao) {
    return MemberResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.isUsedFlag())
        .walletAddress(dao.getWalletAddress())
        .nickname(dao.getNickname())
        .email(dao.getEmail())
        .emailVerifiedFlag(dao.isEmailVerifiedFlag())
        .phoneNumber(dao.getPhoneNumber())
        .phoneVerifiedFlag(dao.isPhoneVerifiedFlag())
        .emailNewsletterFlag(dao.isEmailNewsletterFlag())
        .nationalCode(dao.getNationalCode())
        .referrer(dao.getReferrer())
        .referrerCode(dao.getReferrerCode())
        .otpFlag(dao.isOtpFlag())
        .juminFlag(dao.isJuminFlag())
        .codeFlag(dao.isCodeFlag())
        .code(dao.getCode())
        .codeDate(dao.getCodeDate())
        .approveType(dao.getApproveType())
        .approveId(dao.getApproveId())
        .approveDate(dao.getApproveDate())
        .walletId(dao.getWalletId())
        .migratedFlag(dao.isMigratedFlag())
        .imsiPasswordFlag(dao.isImsiPasswordFlag())
        .cryptobroId(dao.getCryptobroId())
        .migBalance(dao.getMigBalance())
        .build();
  }

  public MemberResponseModel getByEmail(MemberGetByEmailRequestModel memberGetByEmailRequestModel) {
    String email = memberMapper.getByEmail(memberGetByEmailRequestModel.getEmail());

    if (null == email) {
      throw new ServiceException(String.format(Constants.MSG_DUPLICATED_DATA));
    }
    return MemberResponseModel.builder()
      .email(email)
      .build();
  }
  private boolean checkRegexPassword(String password) {
    Pattern pattern = Pattern.compile(
      "^(?=.*[A-Za-z])(?=.*[\\d!@#$%^&*()_+-=\\\\|{}\\[\\]<>?\\\"'`~₩])[A-Za-z0-9!@#$%^&*()_+-=\\\\|{}\\[\\]<>?\\\"'`~₩]{8,}$");
    Matcher matcher = pattern.matcher(password);
    return matcher.find();
  }


  // 이관 체크용
  public ResponseListModel migList(MemberListRequestModel requestModel) {
    ResponseListModel result = new ResponseListModel();

    int totalCount = memberMapper.migCount(requestModel);

    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
//      result.setList(Collections.emptyList());
      return result;
    }

    List<MemberResponseModel> memberDaoList = memberMapper.migList(requestModel).stream()
            .map(this::convertMemberResponseModel).collect(Collectors.toList());

    result.setList(memberDaoList);

    return result;
  }

}
