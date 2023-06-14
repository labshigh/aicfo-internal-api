package com.labshigh.aicfo.internal.api.board.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.board.model.request.BoardReplyDeleteRequestModel;
import com.labshigh.aicfo.internal.api.board.model.request.BoardReplyInsertRequestModel;
import com.labshigh.aicfo.internal.api.board.model.request.BoardReplyListRequestModel;
import com.labshigh.aicfo.internal.api.board.model.request.BoardReplyUpdateRequestModel;
import com.labshigh.aicfo.internal.api.board.service.BoardReplyService;
import com.labshigh.aicfo.internal.api.board.validator.BoardReplyDeleteRequestValidator;
import com.labshigh.aicfo.internal.api.board.validator.BoardReplyInsertRequestValidator;
import com.labshigh.aicfo.internal.api.board.validator.BoardReplyListRequestValidator;
import com.labshigh.aicfo.internal.api.board.validator.BoardReplyUpdateRequestValidator;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
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
@RequestMapping("/api/board/{boardUid}/reply")
public class BoardReplyController {

  @Autowired
  BoardReplyService boardReplyService;

  @ApiOperation("게시판 답변 등록")
  @PostMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertBoardReply(
      @RequestBody BoardReplyInsertRequestModel requestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    BoardReplyInsertRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(boardReplyService.insert(requestModel));
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

  @ApiOperation("게시판 답변 리스트 조회")
  @GetMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listBoardReply(BoardReplyListRequestModel requestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    BoardReplyListRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(boardReplyService.listBoardReply(requestModel));
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

  @ApiOperation("게시판 답변 수정")
  @PutMapping(value = "/{boardReplyUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updateBoardReply(
      @RequestBody BoardReplyUpdateRequestModel requestModel,
      @PathVariable("boardReplyUid") long boardReplyUid,
      BindingResult bindingResult) {

    requestModel.setUid(boardReplyUid);

    ResponseModel responseModel = new ResponseModel();

    BoardReplyUpdateRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(boardReplyService.updateById(requestModel));
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

  @ApiOperation("게시판 답변 삭제")
  @DeleteMapping(value = "/{boardReplyUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> deleteBoardReply(
      @RequestBody BoardReplyDeleteRequestModel requestModel,
      @PathVariable("boardReplyUid") long boardReplyUid,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();
    requestModel.setUid(boardReplyUid);

    BoardReplyDeleteRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        boardReplyService.deleteById(requestModel);
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
