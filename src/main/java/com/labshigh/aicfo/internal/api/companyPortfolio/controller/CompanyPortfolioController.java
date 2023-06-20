package com.labshigh.aicfo.internal.api.companyPortfolio.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyPortfolioDeleteRequestModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyPortfolioDetailRequestModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyPortfolioInsertRequestModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.model.request.CompanyPortfolioUpdateRequestModel;
import com.labshigh.aicfo.internal.api.companyPortfolio.service.CompanyPortfolioService;
import com.labshigh.aicfo.internal.api.companyPortfolio.validator.CompanyPortfolioDeleteRequestValidator;
import com.labshigh.aicfo.internal.api.companyPortfolio.validator.CompanyPortfolioDetailRequestValidator;
import com.labshigh.aicfo.internal.api.companyPortfolio.validator.CompanyPortfolioInsertRequestValidator;
import com.labshigh.aicfo.internal.api.companyPortfolio.validator.CompanyPortfolioUpdateRequestValidator;
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
@RequestMapping("/api/companyPortfolio")
public class CompanyPortfolioController {

  @Autowired
  private CompanyPortfolioService companyPortfolioService;

  @ApiOperation("기업 포트폴리오 상세")
  @GetMapping(value = "/detail/{memberUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> detailCompanyPortfolio(
      CompanyPortfolioDetailRequestModel requestModel,
      @PathVariable("memberUid") long memberUid,
      BindingResult bindingResult) {
    requestModel.setMemberUid(memberUid);

    ResponseModel responseModel = new ResponseModel();

    CompanyPortfolioDetailRequestValidator.builder().build().validate(requestModel, bindingResult);

    if ((bindingResult.hasErrors())) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(companyPortfolioService.detail(requestModel));
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

  @ApiOperation("기업 포트폴리오 등록")
  @PostMapping(value = "/{memberUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertCompanyPortfolio(@RequestBody
  CompanyPortfolioInsertRequestModel requestModel,
      @PathVariable("memberUid") long memberUid,
      BindingResult bindingResult) {

    requestModel.setMemberUid(memberUid);

    ResponseModel responseModel = new ResponseModel();

    CompanyPortfolioInsertRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(companyPortfolioService.insert(requestModel));
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


  @ApiOperation("기업 포트폴리오 수정")
  @PutMapping(value = "/{memberUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updateCompanyPortfolio(@RequestBody
  CompanyPortfolioUpdateRequestModel requestModel,
      @PathVariable("memberUid") long memberUid,
      BindingResult bindingResult) {

    requestModel.setMemberUid(memberUid);

    ResponseModel responseModel = new ResponseModel();

    CompanyPortfolioUpdateRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(companyPortfolioService.update(requestModel));
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

  @ApiOperation("기업 포트폴리오 삭제")
  @PutMapping(value = "/{companyPortfolioUid}/{memberUid}",
      produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> deleteCompanyPortfolio(@RequestBody
  CompanyPortfolioDeleteRequestModel requestModel,
      @PathVariable("memberUid") long memberUid,
      @PathVariable("companyPortfolioUid") long companyPortfolioUid,
      BindingResult bindingResult) {

    requestModel.setUid(companyPortfolioUid);
    requestModel.setMemberUid(memberUid);

    ResponseModel responseModel = new ResponseModel();

    CompanyPortfolioDeleteRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        companyPortfolioService.delete(requestModel);
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
