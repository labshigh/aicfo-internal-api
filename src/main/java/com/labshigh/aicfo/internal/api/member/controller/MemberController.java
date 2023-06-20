package com.labshigh.aicfo.internal.api.member.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.core.utils.StringUtils;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.member.model.request.*;
import com.labshigh.aicfo.internal.api.member.model.response.MemberResponseModel;
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

  @ApiOperation(value = "memberUid 로 member 가져오기")
  @GetMapping(value = "/{memberUid}")
  public ResponseEntity<String> getByiUid(@PathVariable(value = "memberUid") long memberUid) {

    ResponseModel responseModel = new ResponseModel();

    if (memberUid <= 0) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uid"));
    } else {
      try {
        responseModel.setData(memberService.getByUid(memberUid));
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


  @ApiOperation(value = "멤버 가입하기")
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

  @ApiOperation(value = "소셜 연동한 사용자가 있는지 체크")
  @PostMapping(value = "/checkUser", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> getMemberCheck(@RequestBody MemberSigninRequestModel memberSigninRequestModel) {

    ResponseModel responseModel = new ResponseModel();

    try {
      MemberResponseModel model;
      model = memberService.getSocialMemberInfo(memberSigninRequestModel.getEmail());
      if(model.getUid() <= 0) {
        responseModel.setStatus(HttpStatus.NO_CONTENT.value());
        responseModel.setData(MemberResponseModel.builder().email("").build());
      } else {
        responseModel.setData(memberService.getSocialMemberInfo(memberSigninRequestModel.getEmail()));
      }
    } catch (ServiceException e) {
      responseModel.setStatus(HttpStatus.BAD_REQUEST.value());
      responseModel.setMessage(e.getMessage());
      responseModel.error.setErrorCode(HttpStatus.BAD_REQUEST.value());
      responseModel.error.setErrorMessage(e.getLocalizedMessage());
    } catch (Exception e) {
      responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
      responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseModel.error.setErrorMessage(e.getLocalizedMessage());
    }
    return responseModel.toResponse();
  }


}
