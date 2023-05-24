package com.labshigh.aicfo.internal.api.sms.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.sms.model.request.AdminSendSmsVerifyRequestModel;
import com.labshigh.aicfo.internal.api.sms.model.request.AdminVerifySmsRequestModel;
import com.labshigh.aicfo.internal.api.sms.service.SmsService;
import com.labshigh.aicfo.internal.api.sms.validator.AdminSendSmsVerifyRequestValidator;
import com.labshigh.aicfo.internal.api.sms.validator.AdminVerifySmsRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/api/sms")
public class SmsController {

  private final SmsService smsService;

  public SmsController(SmsService smsService) {
    this.smsService = smsService;
  }

  @ApiOperation(value = "sms 인증 문자 발송")
  @PostMapping(value = "/admin/sendVerifySms", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseModel adminSendVerifySms(
      @RequestBody AdminSendSmsVerifyRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    AdminSendSmsVerifyRequestValidator.builder().build().validate(requestModel,
        bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel = smsService.adminSendVerifySms(requestModel);
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
      @RequestBody AdminVerifySmsRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    AdminVerifySmsRequestValidator.builder().build().validate(requestModel,
        bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(smsService.verifySms(requestModel));
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
}
