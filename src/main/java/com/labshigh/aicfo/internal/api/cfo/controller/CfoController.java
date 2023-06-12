package com.labshigh.aicfo.internal.api.cfo.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.cfo.model.request.CfoListRequestModel;
import com.labshigh.aicfo.internal.api.cfo.service.CfoService;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/cfo")
public class CfoController {

  @Autowired
  CfoService cfoService;

  @ApiOperation("cfo 리스트")
  @GetMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listCfo(CfoListRequestModel requestModel) {
    ResponseModel responseModel = new ResponseModel();

    try {
      responseModel.setData(cfoService.listCfo(requestModel));
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


}
