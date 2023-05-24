package com.labshigh.aicfo.internal.api.totp.service;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.member.dao.MemberDao;
import com.labshigh.aicfo.internal.api.member.mapper.MemberMapper;
import com.labshigh.aicfo.internal.api.totp.mapper.MemberTotpMapper;
import com.labshigh.aicfo.internal.api.totp.model.response.TotpActivateResponseModel;
import com.labshigh.aicfo.internal.api.totp.model.response.TotpCreateResponseModel;
import com.labshigh.aicfo.internal.api.totp.model.response.TotpDetailResponseModel;
import com.labshigh.aicfo.internal.api.totp.model.response.TotpVerifyResponseModel;
import com.labshigh.aicfo.core.utils.JsonUtils;
import com.labshigh.aicfo.internal.api.totp.dao.MemberTotpDAO;
import com.labshigh.aicfo.internal.api.totp.model.request.*;
import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.recovery.RecoveryCodeGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

@Log4j2
@Service
public class TotpService {

  @Value("${aicfo.totp.issuer}")
  private String issuer;
  @Value("${aicfo.totp.digits}")
  private int digits;
  @Value("${aicfo.totp.period}")
  private int period;
  @Value("${aicfo.totp.secret.length}")
  private int secretLength;
  @Value("${aicfo.totp.allowedTimePeriodDiscrepancy}")
  private int allowedTimePeriodDiscrepancy;

  private final MemberTotpMapper memberTotpMapper;
  private final MemberMapper memberMapper;

  public TotpService(MemberTotpMapper memberTotpMapper, MemberMapper memberMapper) {
    this.memberTotpMapper = memberTotpMapper;
    this.memberMapper = memberMapper;
  }

  @Transactional
  public TotpCreateResponseModel create(TotpCreateRequestModel requestModel) throws QrGenerationException {

    MemberTotpDAO memberTotpDAO = memberTotpMapper.detail(MemberTotpDAO.builder()
        .memberUid(requestModel.getMemberUid())
        .usedFlag(true)
        .build());

    if (memberTotpDAO != null) {
      throw new ServiceException(String.format(Constants.MSG_TOTP_ALREADY_USED));
    }

    MemberDao memberDao = memberMapper.get(MemberDao.builder().uid(requestModel.getMemberUid()).build());

    if (memberDao == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    String label = issuer + ":" + memberDao.getWalletAddress();

    SecretGenerator secretGenerator = new DefaultSecretGenerator(secretLength);
    String secret = secretGenerator.generate();

    log.debug(secret);

    QrData qrData = new QrData.Builder()
        .label(label)
        .secret(secret)
        .issuer(issuer)
        .algorithm(HashingAlgorithm.SHA1)
        .digits(digits)
        .period(period)
        .build();

    QrGenerator qrGenerator = new ZxingPngQrGenerator();

    String dataUri = getDataUriForImage(qrGenerator.generate(qrData), qrGenerator.getImageMimeType());

    RecoveryCodeGenerator recoveryCodes = new RecoveryCodeGenerator();
    List<String> codes = new ArrayList(Arrays.asList(recoveryCodes.generateCodes(16)));

    memberTotpMapper.insert(MemberTotpDAO.builder()
        .secret(secret)
        .recoveryCodes(JsonUtils.convertObjectToJsonString(codes))
        .memberUid(memberDao.getUid())
        .usedFlag(requestModel.isUsedFlag())
        .build());

    return TotpCreateResponseModel.builder()
        .barcode(dataUri)
        .uri(qrData.getUri())
        .secret(secret)
        .build();
  }

  @Transactional
  public TotpActivateResponseModel activate(TotpActivateRequestModel requestModel) {

    MemberTotpDAO memberTotpDAO1 = memberTotpMapper.detail(MemberTotpDAO.builder()
        .memberUid(requestModel.getMemberUid())
        .usedFlag(true)
        .build());

    if (memberTotpDAO1 != null) {
      throw new ServiceException(String.format(Constants.MSG_TOTP_ALREADY_USED));
    }

    MemberTotpDAO memberTotpDAO2 = memberTotpMapper.detail(MemberTotpDAO.builder()
        .memberUid(requestModel.getMemberUid())
        .usedFlag(false)
        .build());

    if (memberTotpDAO2 == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    TimeProvider timeProvider = new SystemTimeProvider();
    CodeGenerator codeGenerator = new DefaultCodeGenerator();
    DefaultCodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

    // sets the time period for codes to be valid for to 30 seconds
    verifier.setTimePeriod(period);

    // allow codes valid for 2 time periods before/after to pass as valid
    verifier.setAllowedTimePeriodDiscrepancy(allowedTimePeriodDiscrepancy);

    boolean verifyCode = verifier.isValidCode(memberTotpDAO2.getSecret(), requestModel.getCode());
    String recoveryCodes = null;

    if (verifyCode == true) {

      memberTotpMapper.activate(MemberTotpDAO.builder()
          .memberUid(requestModel.getMemberUid())
          .build());

      recoveryCodes = memberTotpDAO2.getRecoveryCodes();
    }

    return TotpActivateResponseModel.builder()
        .activate(verifyCode)
        .recoveryCodes(JsonUtils.convertJsonStringToObject(recoveryCodes))
        .build();
  }

  @Transactional
  public void delete(TotpDeleteRequestModel requestModel) {

    MemberTotpDAO memberTotpDAO = memberTotpMapper.detail(MemberTotpDAO.builder()
        .memberUid(requestModel.getMemberUid())
        .usedFlag(true)
        .build());

    if (memberTotpDAO == null) {
      throw new ServiceException(String.format(Constants.MSG_TOTP_NOT_USED));
    }

    memberTotpMapper.delete(MemberTotpDAO.builder()
        .memberUid(requestModel.getMemberUid())
        .build());
  }

  public TotpDetailResponseModel detail(TotpDetailRequestModel requestModel) throws QrGenerationException {

    MemberTotpDAO memberTotpDAO = memberTotpMapper.detail(MemberTotpDAO.builder()
        .memberUid(requestModel.getMemberUid())
        .usedFlag(true)
        .build());

    if (memberTotpDAO == null) {
      throw new ServiceException(String.format(Constants.MSG_TOTP_NOT_USED));
    }

    String label = issuer + ":" + memberTotpDAO.getEmail();

    QrData qrData = new QrData.Builder()
        .label(label)
        .secret(memberTotpDAO.getSecret())
        .issuer(issuer)
        .algorithm(HashingAlgorithm.SHA1)
        .digits(digits)
        .period(period)
        .build();

    QrGenerator qrGenerator = new ZxingPngQrGenerator();

    String dataUri = getDataUriForImage(qrGenerator.generate(qrData), qrGenerator.getImageMimeType());

    return TotpDetailResponseModel.builder()
        .digits(digits)
        .period(period)
        .email(memberTotpDAO.getEmail())
        .issuer(issuer)
        .barcode(dataUri)
        .uri(qrData.getUri())
        .recoveryCodes(JsonUtils.convertJsonStringToObject(memberTotpDAO.getRecoveryCodes()))
        .build();
  }

  public TotpVerifyResponseModel verify(TotpVerifyRequestModel requestModel) {

    MemberTotpDAO memberTotpDAO = memberTotpMapper.detail(MemberTotpDAO.builder()
        .memberUid(requestModel.getMemberUid())
        .usedFlag(true)
        .build());

    if (memberTotpDAO == null) {
      throw new ServiceException(String.format(Constants.MSG_TOTP_NOT_USED));
    }

    TimeProvider timeProvider = new SystemTimeProvider();
    CodeGenerator codeGenerator = new DefaultCodeGenerator();
    DefaultCodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

    // sets the time period for codes to be valid for to 30 seconds
    verifier.setTimePeriod(period);

    // allow codes valid for 2 time periods before/after to pass as valid
    verifier.setAllowedTimePeriodDiscrepancy(allowedTimePeriodDiscrepancy);

    return TotpVerifyResponseModel.builder()
        .verify(verifier.isValidCode(memberTotpDAO.getSecret(), requestModel.getCode()))
        .build();
  }
}
