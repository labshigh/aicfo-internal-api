package com.labshigh.aicfo.internal.api.sms.service;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.admin.dao.AdminDAO;
import com.labshigh.aicfo.internal.api.admin.mapper.AdminMapper;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.common.utils.SmsUtils;
import com.labshigh.aicfo.internal.api.common.utils.models.SmsMessage;
import com.labshigh.aicfo.internal.api.common.utils.models.SmsRequestModel;
import com.labshigh.aicfo.internal.api.sms.model.request.AdminSendSmsVerifyRequestModel;
import com.labshigh.aicfo.internal.api.sms.model.request.AdminVerifySmsRequestModel;
import com.labshigh.aicfo.internal.api.sms.model.response.VerifySmsResponseModel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SmsService {

  @Autowired
  private AdminMapper adminMapper;
  @Autowired
  private SmsUtils smsUtils;
  @Autowired
  private RedisTemplate redisTemplate;

  @Value("${ncloud.sms-storage.from-phone-number}")
  private String fromPhoneNumber;
  @Value("${ncloud.sms-storage.expirationTime}")
  private long smsExpirationTime;


  public ResponseModel adminSendVerifySms(AdminSendSmsVerifyRequestModel requestModel) {
    AdminDAO dao = adminMapper.detail(requestModel.getAdminUid());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }
    if (StringUtils.isEmpty(dao.getMobile())) {
      throw new ServiceException(
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "d.mobile.b.required"));
    }

    if (StringUtils.isEmpty(dao.getNationalCode())) {
      throw new ServiceException(
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "d.nationalCode.b.required"));
    }

//    if (dao.getNationalCode().equals(requestModel.getNationalCode())
//        && dao.getPhoneNumber().equals(requestModel.getPhoneNumber())) {
//      throw new ServiceException(Constants.MSG_ALREADY_VERIFIED_SMS);
//    }

    String verifyCode = RandomStringUtils.randomNumeric(6);

    String smsContent = requestModel.getMenuId().equals("SignIn") ? String.format(
        Constants.MSG_AMDIN_LOGIN_VERIFY_CONTENT_SMS, verifyCode)
        : String.format(Constants.MSG_AMDIN_WITHDRAWAL_VERIFY_CONTENT_SMS, verifyCode);

    List<SmsMessage> smsMessages = new ArrayList<>();
    smsMessages.add(SmsMessage.builder()
        .to(dao.getMobile())
        .content(smsContent)
        .build());

    SmsRequestModel smsRequestModel = SmsRequestModel.builder()
        .type("SMS")
        .contentType("COMM")
        .countryCode(dao.getNationalCode())
        .from(fromPhoneNumber)
        .content(smsContent)
        .messages(smsMessages)
        .build();
    ResponseModel responseModel = smsUtils.send(smsRequestModel);

    redisTemplate.opsForValue()
        .set("aicfo" + requestModel.getMenuId() + "VerifyPhoneNumber:"
                + dao.getNationalCode()
                + dao.getMobile(),
            verifyCode, smsExpirationTime, TimeUnit.SECONDS);

    return responseModel;
  }

  public VerifySmsResponseModel verifySms(AdminVerifySmsRequestModel requestModel) {

    AdminDAO dao = adminMapper.detail(requestModel.getAdminUid());
    boolean smsVerifyFlag;

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }

    if (!dao.getNationalCode().equals(requestModel.getNationalCode())
        || !dao.getMobile().equals(requestModel.getMobile())) {
      throw new ServiceException(Constants.MSG_VALUE_ERROR);
    }

    String rediskey = "aicfo" + requestModel.getMenuId() + "VerifyPhoneNumber:"
        + requestModel.getNationalCode()
        + requestModel.getMobile();

    // TODO : verifiedCode 로 인증번호 체크
    Object verifiedCodeObj = redisTemplate.opsForValue().get(rediskey);

    if (verifiedCodeObj == null || !requestModel.getVerifyCode()
        .equals(verifiedCodeObj.toString())) {
      //throw new ServiceException(Constants.MSG_TOKEN_ERROR);
      smsVerifyFlag = false;
    } else {
      smsVerifyFlag = true;
      redisTemplate.delete(rediskey);
    }

    return VerifySmsResponseModel.builder().smsVerifyFlag(smsVerifyFlag).build();


  }


}
