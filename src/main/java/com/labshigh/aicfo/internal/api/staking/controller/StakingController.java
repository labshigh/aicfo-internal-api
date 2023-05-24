package com.labshigh.aicfo.internal.api.staking.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.staking.model.request.AdminWalletInsertRequestModel;
import com.labshigh.aicfo.internal.api.staking.model.request.AdminWalletUpdateRequestModel;
import com.labshigh.aicfo.internal.api.staking.service.StakingService;
import com.labshigh.aicfo.internal.api.staking.validator.AdminWalletInsertRequestValidator;
import com.labshigh.aicfo.internal.api.staking.validator.AdminWalletUpdateRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/staking")
public class StakingController {

  @Autowired
  private StakingService stakingService;

  @ApiOperation("관리자 지갑 주소 조회")
  @GetMapping(value = "/{adminUid}/walletAddress", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> walletAddressList(@PathVariable("adminUid") long adminUid) {
    ResponseModel responseModel = new ResponseModel();
    if (adminUid < 1) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "adminUid"));
    } else {
      try {
        responseModel.setData(stakingService.listAdminWallet(adminUid));
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

  @ApiOperation("관리자 지갑 등록")
  @PostMapping(value = "/{adminUid}/walletAddress", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertWalletAddress(
      @RequestBody AdminWalletInsertRequestModel requestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    AdminWalletInsertRequestValidator.builder().build().validate(requestModel, bindingResult);
    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        stakingService.insertAdminWallet(requestModel);
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

  @ApiOperation("관리자 지갑 정보 수정")
  @PutMapping(value = "/{adminWalletUid}/walletAddress", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updateWalletAddress(
      @RequestBody AdminWalletUpdateRequestModel requestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    AdminWalletUpdateRequestValidator.builder().build().validate(requestModel, bindingResult);
    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        stakingService.updateAdminWallet(requestModel);
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

  @ApiOperation("관리자 지갑 정보 삭제")
  @DeleteMapping(value = "/{adminWalletUid}/walletAddress", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> DeleteWalletAddress(
      @RequestBody AdminWalletUpdateRequestModel requestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    AdminWalletUpdateRequestValidator.builder().build().validate(requestModel, bindingResult);
    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        stakingService.deleteAdminWallet(requestModel);
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
