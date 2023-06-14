package com.labshigh.aicfo.internal.api.inquiry.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.inquiry.model.request.InquiryInsertRequestModel;
import com.labshigh.aicfo.internal.api.inquiry.model.request.InquiryListRequestModel;
import com.labshigh.aicfo.internal.api.inquiry.model.request.InquiryUpdateRequestModel;
import com.labshigh.aicfo.internal.api.inquiry.service.InquiryService;
import com.labshigh.aicfo.internal.api.inquiry.validator.InquiryInsertRequestValidator;
import com.labshigh.aicfo.internal.api.inquiry.validator.InquiryListRequestValidator;
import com.labshigh.aicfo.internal.api.inquiry.validator.InquiryUpdateInquiryTimeRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("api/inquiry")
public class InquiryController {

  @Autowired
  private InquiryService inquiryService;

  @ApiOperation("문의 리스트")
  @GetMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listInquiry(InquiryListRequestModel requestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    InquiryListRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(inquiryService.listInquiry(requestModel));
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

  @ApiOperation("문의 등록")
  @PostMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertInquiry(@RequestBody InquiryInsertRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    InquiryInsertRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(inquiryService.insertInquiry(requestModel));
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

  @ApiOperation("상담 시간 업데이트")
  @PutMapping(value = "/{inquiryUid}/updateInquiryTime", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updateInquiryTime(
      @RequestBody InquiryUpdateRequestModel requestModel,
      @PathVariable("inquiryUid") long inquiryUid,
      BindingResult bindingResult) {

    requestModel.setUid(inquiryUid);

    ResponseModel responseModel = new ResponseModel();

    InquiryUpdateInquiryTimeRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        inquiryService.updateInquiryTime(requestModel);
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
