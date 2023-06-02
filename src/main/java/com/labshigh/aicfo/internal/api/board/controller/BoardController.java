package com.labshigh.aicfo.internal.api.board.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.board.model.request.BoardDetailRequestModel;
import com.labshigh.aicfo.internal.api.board.model.request.BoardListRequestModel;
import com.labshigh.aicfo.internal.api.board.model.request.BoardUpdateRecommendCountRequestModel;
import com.labshigh.aicfo.internal.api.board.service.BoardService;
import com.labshigh.aicfo.internal.api.board.validator.BoardDetailRequestValidator;
import com.labshigh.aicfo.internal.api.board.validator.BoardListRequestValidator;
import com.labshigh.aicfo.internal.api.board.validator.BoardUpdateRecommendCountRequestValidator;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/board")
public class BoardController {

  @Autowired
  BoardService boardService;

  @ApiOperation("게시판 리스트")
  @GetMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listBoard(BoardListRequestModel requestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    BoardListRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(boardService.listBoard(requestModel));
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

  @ApiOperation("게시판 상세")
  @GetMapping(value = "/{boardUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> detailBoard(@PathVariable("boardUid") long boardUid,
      BoardDetailRequestModel requestModel,
      BindingResult bindingResult) {

    requestModel.setBoardUid(boardUid);

    ResponseModel responseModel = new ResponseModel();

    BoardDetailRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(boardService.detailBoard(requestModel));
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

  @ApiOperation("게시판 추천 업데이트")
  @PutMapping(value = "/{boardUid}/recommend", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updateRecommendCount(
      @PathVariable("boardUid") long boardUid,
      @RequestBody BoardUpdateRecommendCountRequestModel requestModel,
      BindingResult bindingResult) {

    requestModel.setBoardUid(boardUid);

    ResponseModel responseModel = new ResponseModel();

    BoardUpdateRecommendCountRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        boardService.updateRecommendCount(requestModel);
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
