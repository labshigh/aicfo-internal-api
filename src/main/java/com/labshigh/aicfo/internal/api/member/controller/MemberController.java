package com.labshigh.aicfo.internal.api.member.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.member.model.request.*;
import com.labshigh.aicfo.internal.api.member.service.MemberService;
import com.labshigh.aicfo.internal.api.member.validator.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@RestController
@RequestMapping(value = "/api/member")
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @ApiOperation("Member 리스트 조회")
  @GetMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> list(MemberListRequestModel memberListRequestModel,
                                     BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MemberListRequestValidator.builder().build()
      .validate(memberListRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.list(memberListRequestModel));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }
  @ApiOperation(value = "이메일 멤버 가입하기")
  @PostMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertEmailMember(
    @RequestBody MemberInsertRequestModel memberInsertRequestModel,
    BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    MemberInsertRequestValidator.builder().build().validate(
      memberInsertRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.insertByEmail(memberInsertRequestModel));
      } catch (AuthenticationException e) {
        responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseModel.setMessage(e.getMessage());
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }

  @ApiOperation(value = "MetaMask Wallet 저장하기")
  @PostMapping(value = "/metamask", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertMetaMask(
      @RequestBody MemberInsertMetaMaskRequestModel memberInsertMetaMaskRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    MemberInsertMetaMaskRequestValidator.builder().build().validate(
        memberInsertMetaMaskRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.insertMetaMask(memberInsertMetaMaskRequestModel));
      } catch (AuthenticationException e) {
        responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseModel.setMessage(e.getMessage());
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }

  @ApiOperation(value = "Sign In")
  @PostMapping(value = "/signin", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> signin(@RequestBody MemberSigninRequestModel memberSigninRequestModel, BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    MemberSigninRequestValidator.builder().build().validate(
      memberSigninRequestModel, bindingResult);

    if(bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.signin(memberSigninRequestModel));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }

    return responseModel.toResponse();
  }

  @ApiOperation(value = "Sign In wallet")
  @PostMapping(value = "/signin/wallet", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> signinWallet(@RequestBody SigninWalletRequestModel requestModel, BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    SigninWalletRequestValidator.builder().build().validate(
      requestModel, bindingResult);

    if(bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.signinWallet(requestModel));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }

    return responseModel.toResponse();
  }

  @ApiOperation(value = "memberUid 로 member 가져오기")
  @GetMapping(value = "/{memberUid}/wallet/{walletId}/info")
  public ResponseEntity<String> getByiUid(@PathVariable(value = "memberUid") long memberUid,@PathVariable(value = "walletId") long walletId) {

    ResponseModel responseModel = new ResponseModel();

    if (memberUid <= 0) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uid"));
    } else {
      try {
        responseModel.setData(memberService.getByUid(memberUid,walletId));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }

  @ApiOperation(value = "walletAddress 로 member 가져오기")
  @GetMapping(value = "/byWallet/{walletAddress}")
  public ResponseEntity<String> getByWalletAddress(
      @PathVariable(value = "walletAddress") String walletAddress,
      MemberGetByWalletAddressRequestModel memberGetByWalletAddressRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    memberGetByWalletAddressRequestModel.setWalletAddress(walletAddress);

    MemberGetByWalletAddressRequestValidator.builder().build().validate(
        memberGetByWalletAddressRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(
            memberService.getByWalletAddress(memberGetByWalletAddressRequestModel));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }

    return responseModel.toResponse();
  }

  @ApiOperation(value = "인증메일 발송")
  @PostMapping(value = "/{memberUid}/verifyEmail", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> sendVerifyEmail(@PathVariable(value = "memberUid") long memberUid,
      @RequestBody MemberSendVerifyEmailRequestModel memberSendVerifyEmailRequestModel,
      BindingResult bindingResult) {
    memberSendVerifyEmailRequestModel.setMemberUid(memberUid);

    ResponseModel responseModel = new ResponseModel();

    MemberSendVerifyEmailRequestValidator.builder().build()
        .validate(memberSendVerifyEmailRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {

      try {

        responseModel = memberService.sendVerifyEmail(memberSendVerifyEmailRequestModel);
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }

    return responseModel.toResponse();
  }

  @ApiOperation(value = "token을 이용한 email 인증 하기")
  @PutMapping(value = "/verifyEmail/{token}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> verifyEmail(@PathVariable(value = "token") String token) {
    ResponseModel responseModel = new ResponseModel();
    try {

      if (StringUtils.isEmpty(token)) {
        throw new ServiceException(Constants.MSG_TOKEN_ERROR);
      }
      memberService.verifyEmail(token);
    } catch (ServiceException e) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(e.getMessage());
    } catch (Exception e) {
      responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
      responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseModel.error.setErrorMessage(e.getLocalizedMessage());
    }

    return responseModel.toResponse();
  }

  @ApiOperation(value = "sms 인증 문자 발송")
  @PostMapping(value = "/sendVerifySms", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseModel sendVerifySms(
      @RequestBody MemberSendSmsVerifyRequestModel memberSendSmsVerifyRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MemberSendSmsVerifyRequestValidator.builder().build().validate(memberSendSmsVerifyRequestModel,
        bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        memberService.sendVerifySms(memberSendSmsVerifyRequestModel);
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel;
  }

  @ApiOperation(value = "sms 인증")
  @PutMapping(value = "/verifySms", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseModel verifySms(
      @RequestBody MemberVerifySmsRequestModel memberVerifySmsRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MemberVerifySmsRequestValidator.builder().build().validate(memberVerifySmsRequestModel,
        bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        memberService.verifySms(memberVerifySmsRequestModel);
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel;
  }

  @ApiOperation(value = "멤버 부가 정보 저장")
  @PutMapping(value = "/{memberUid}/updateEtcInfo", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updateEtcInfo(
      @RequestBody MemberUpdateEtcInfoRequestModel memberUpdateEtcInfoRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MemberUpdateEtcInfoRequestValidator.builder().build().validate(memberUpdateEtcInfoRequestModel,
        bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.updateEtcInfo(memberUpdateEtcInfoRequestModel));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }

  @ApiOperation(value = "email 로 member 가져오기")
  @GetMapping(value = "/byEmail")
  public ResponseEntity<String> getByEmail(MemberGetByEmailRequestModel memberGetByEmailRequestModel, BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    MemberGetByEmailRequestValidator.builder().build().validate(
      memberGetByEmailRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.getByEmail(memberGetByEmailRequestModel));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }

    return responseModel.toResponse();
  }

  @ApiOperation("사용자 인증 이미지 업로드")
  @PostMapping(value = "/file", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> upload(MemberImageUploadRequestModel memberImageUploadRequestModel,
                                       BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MemberImageUploadRequestValidator.builder().build()
      .validate(memberImageUploadRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.upload(memberImageUploadRequestModel));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }

  @ApiOperation("사용자 인증 신분증 Viewer")
  @PostMapping(value = "/file/viewer", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> fileViewer(@RequestBody MemberImageViewerRequestModel memberImageViewerRequestModel,
                               HttpServletResponse response,
                               BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MemberImageViewerRequestValidator.builder().build()
      .validate(memberImageViewerRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.fileViewer(memberImageViewerRequestModel));


      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }

  @ApiOperation("사용자 코드 문자 발송")
  @PostMapping(value = "/code", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> sendCode(@RequestBody MemberUpdateCodeRequestModel memberUpdateCodeRequestModel,
                                       BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MemberUpdateCodeRequestValidator.builder().build()
      .validate(memberUpdateCodeRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.sendCode(memberUpdateCodeRequestModel));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }

  @ApiOperation(value = "멤버 인증")
  @PutMapping(value = "/approve", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> approve(
    @RequestBody MemberApporveRequestModel memberApporveRequestModel,
    BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MemberApproveRequestValidator.builder().build().validate(memberApporveRequestModel,
      bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.approve(memberApporveRequestModel));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }
  @ApiOperation(value = "member referrer 수정")
  @PutMapping(value = "/referrer", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updateReferrer(@RequestBody MemberUpdateReferrerRequestModel requestModel,
                                               BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();


    MemberUpdateReferrerRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.updateReferrer(requestModel));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }

  @ApiOperation(value = "member 추천코드 발급")
  @PostMapping(value = "/referrer-code", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updateReferrerCode(@RequestBody MemberUpdateReferrerRequestModel requestModel,
                                               BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();


    MemberUpdateReferrerCodeRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.updateReferrerCode(requestModel));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }
  @ApiOperation(value = "member nickname 수정")
  @PutMapping(value = "/nickname", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updateNickname(@RequestBody MemberUpdateNicknameRequestModel requestModel,
                                               BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();


    MemberUpdateNicknameRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.updateNickname(requestModel));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }

  @ApiOperation(value = "member password 수정")
  @PutMapping(value = "/password", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updatePassword(
                                       @RequestBody MemberUpdatePasswordRequestModel requestModel,
                                       BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    MemberUpdatePasswordRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        memberService.updatePassword(requestModel);
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }

  @ApiOperation(value = "패스워드 변경 메일 발송")
  @PostMapping(value = "/find-password", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> sendFindPassword(
                                                @RequestBody MemberSendEmaiFindPasswordlRequestModel memberSendEmaiFindPasswordlRequestModel,
                                                BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    MemberSendEmailFindPasswordRequestValidator.builder().build()
      .validate(memberSendEmaiFindPasswordlRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {

      try {

        responseModel = memberService.sendEmailFindPassword(memberSendEmaiFindPasswordlRequestModel);
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }

    return responseModel.toResponse();
  }

  @ApiOperation(value = "크립토브로 회원 조회")
  @GetMapping(value = "/find-id/cryptobros", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> findIdCryptobros(
    @RequestParam(value = "id") String id) {

    ResponseModel responseModel = new ResponseModel();

    try {
      responseModel.setData(memberService.findIdCryptobros(id));
    } catch (ServiceException e) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(e.getMessage());
    } catch (Exception e) {
      responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
      responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseModel.error.setErrorMessage(e.getLocalizedMessage());
    }

    return responseModel.toResponse();
  }

  @ApiOperation(value = "패스워드 임시 발급 메일 발송 (마이그레이션 회원)")
  @PostMapping(value = "/find-password/imsi", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> sendFindPasswordImsi(
    @RequestBody MemberSendEmaiFindPasswordlRequestModel memberSendEmaiFindPasswordlRequestModel,
    BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    MemberSendEmailFindPasswordRequestValidator.builder().build()
      .validate(memberSendEmaiFindPasswordlRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {

      try {
        responseModel = memberService.sendEmailFindPasswordImsi(memberSendEmaiFindPasswordlRequestModel);
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }

    return responseModel.toResponse();
  }

  @ApiOperation(value = "token을 이용하여 패스워드 변경")
  @PutMapping(value = "/password/{token}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updatePassword(@PathVariable(value = "token") String token,
     @RequestBody MemberUpdatePasswordRequestModel requestModel) {
    ResponseModel responseModel = new ResponseModel();
    try {

      if (StringUtils.isEmpty(token)) {
        throw new ServiceException(Constants.MSG_TOKEN_ERROR);
      }
      requestModel.setToken(token);
      memberService.updatePasswordWithToken(requestModel);
    } catch (ServiceException e) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(e.getMessage());
    } catch (Exception e) {
      responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
      responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseModel.error.setErrorMessage(e.getLocalizedMessage());
    }

    return responseModel.toResponse();
  }
  @ApiOperation(value = "token을 확인")
  @GetMapping(value = "/password/{token}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> checkToken(@PathVariable(value = "token") String token) {
    ResponseModel responseModel = new ResponseModel();
    try {

      if (StringUtils.isEmpty(token)) {
        throw new ServiceException(Constants.MSG_TOKEN_ERROR);
      }
      responseModel.setData(memberService.checkToken(token, Constants.MailType.FIND_PASSWORD));
    } catch (ServiceException e) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(e.getMessage());
    } catch (Exception e) {
      responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
      responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseModel.error.setErrorMessage(e.getLocalizedMessage());
    }

    return responseModel.toResponse();
  }

  // 이관 체크용
  @ApiOperation("Member 멤버 이관 리스트 조회")
  @GetMapping(value = "/migList", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> migList(MemberListRequestModel memberListRequestModel,
                                     BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MemberListRequestValidator.builder().build()
            .validate(memberListRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.migList(memberListRequestModel));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }


}
