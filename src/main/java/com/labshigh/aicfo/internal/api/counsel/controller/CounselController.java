package com.labshigh.aicfo.internal.api.counsel.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.counsel.model.request.CounselDetailRequestModel;
import com.labshigh.aicfo.internal.api.counsel.model.request.CounselInsertRequestModel;
import com.labshigh.aicfo.internal.api.counsel.model.request.CounselListRequestModel;
import com.labshigh.aicfo.internal.api.counsel.service.CounselService;
import com.labshigh.aicfo.internal.api.counsel.validator.CounselInsertRequestValidator;
import com.labshigh.aicfo.internal.api.counsel.validator.CounselListRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/counsel")
public class CounselController {

  @Autowired
  CounselService counselService;

  @ApiOperation("상담 리스트")
  @GetMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listCounsel(CounselListRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    CounselListRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(counselService.listCounsel(requestModel));
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

  @ApiOperation("상담 등록")
  @PostMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertCounsel(@RequestBody CounselInsertRequestModel requestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();
    CounselInsertRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(counselService.insertCounsel(requestModel));
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

  @ApiOperation("상담 상세")
  @GetMapping(value = "/{counselUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> detailCounsel(@PathVariable("counselUid") long counselUid,
      CounselDetailRequestModel requestModel,
      BindingResult bindingResult) {

    requestModel.setCounselUid(counselUid);
    return null;
  }

}
