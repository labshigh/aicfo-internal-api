package com.labshigh.aicfo.internal.api.transaction.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.transaction.model.request.TransactionEventWalletInsertRequestModel;
import com.labshigh.aicfo.internal.api.transaction.model.request.TransactionHistoryRequestModel;
import com.labshigh.aicfo.internal.api.transaction.model.request.TransactionHistoryWalletInsertRequestModel;
import com.labshigh.aicfo.internal.api.transaction.service.TransactionHistoryService;
import com.labshigh.aicfo.internal.api.transaction.validator.TransactionEventInsertRequestValidator;
import com.labshigh.aicfo.internal.api.transaction.validator.TransactionHistoryInsertRequestValidator;
import com.labshigh.aicfo.internal.api.transaction.validator.TransactionHistoryRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/transaction")
public class TransactionHistoryController {

  @Autowired
  private TransactionHistoryService transactionHistoryService;

  @ApiOperation("거래내역 히스토리 조회")
  @GetMapping(value = "/history", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> list(
      TransactionHistoryRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    TransactionHistoryRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(transactionHistoryService.list(requestModel));
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

  @ApiOperation("거래내역 입금 webhook 히스토리 입력")
  @PostMapping(value = "/history", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insert(
    @RequestBody TransactionHistoryWalletInsertRequestModel requestModel,
    BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    TransactionHistoryInsertRequestValidator.builder().build()
      .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        transactionHistoryService.insertWebhook(requestModel);
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

  @ApiOperation("거래내역 입금 webhook Event 입력")
  @PostMapping(value = "/event", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertEvent(
    @RequestBody TransactionEventWalletInsertRequestModel requestModel,
    BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    TransactionEventInsertRequestValidator.builder().build()
      .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        transactionHistoryService.insertEvent(requestModel);
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
