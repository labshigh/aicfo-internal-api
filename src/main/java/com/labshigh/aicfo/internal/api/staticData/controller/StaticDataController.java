package com.labshigh.aicfo.internal.api.staticData.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.staticData.model.request.StaticDataDetailRequestModel;
import com.labshigh.aicfo.internal.api.staticData.service.StaticDataService;
import com.labshigh.aicfo.internal.api.staticData.validator.StaticDataDetailRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/api/staticData")
public class StaticDataController {

  @Autowired
  StaticDataService staticDataService;

  @ApiOperation(value = "StaticData 상세")
  @GetMapping(value = "/{staticDataUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> detail(@PathVariable(value = "staticDataUid") long staticDataUid,
      StaticDataDetailRequestModel requestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    StaticDataDetailRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "commonCodeUid"));
    } else {
      try {
        responseModel.setData(staticDataService.detail(requestModel));

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
