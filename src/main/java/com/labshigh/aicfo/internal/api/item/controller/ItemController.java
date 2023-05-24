package com.labshigh.aicfo.internal.api.item.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.item.model.request.ItemInsertRequestModel;
import com.labshigh.aicfo.internal.api.item.model.request.ItemListRequestModel;
import com.labshigh.aicfo.internal.api.item.model.request.ItemSortUpdateRequestModel;
import com.labshigh.aicfo.internal.api.item.model.request.MarketItemInsertRequestModel;
import com.labshigh.aicfo.internal.api.item.service.ItemService;
import com.labshigh.aicfo.internal.api.item.validator.ItemInsertRequestValidator;
import com.labshigh.aicfo.internal.api.item.validator.ItemListRequestValidator;
import com.labshigh.aicfo.internal.api.item.validator.ItemSortUpdateRequestValidator;
import com.labshigh.aicfo.internal.api.item.validator.MarketItemInsertRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/item")
public class ItemController {

  @Autowired
  private ItemService itemService;

  @ApiOperation("Item 등록")
  @PostMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insert(@RequestBody ItemInsertRequestModel itemInsertRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    ItemInsertRequestValidator.builder().build().validate(itemInsertRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(itemService.insert(itemInsertRequestModel));
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

  @ApiOperation("Item 조회")
  @GetMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listItem(
      ItemListRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    ItemListRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(itemService.list(requestModel));
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

  @ApiOperation("Item 판매 정보 등록")
  @PostMapping(value = "/{itemUid}/marketItem", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertMarketItem(
      @RequestBody MarketItemInsertRequestModel marketItemInsertRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MarketItemInsertRequestValidator.builder().build()
        .validate(marketItemInsertRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(itemService.insertMarketItem(marketItemInsertRequestModel));
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

  @ApiOperation("Item 정렬 또는 사용 여부 업데이트")
  @PutMapping(value = "/{itemUid}/updateSort", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updateSortByAdmin(
      @RequestBody ItemSortUpdateRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    ItemSortUpdateRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        itemService.updateSortByAdmin(requestModel);
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

  @ApiOperation("Item 초기화")
  @PostMapping(value = "/init", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> init() {
    ResponseModel responseModel = new ResponseModel();

    try {
        itemService.initItem();
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
