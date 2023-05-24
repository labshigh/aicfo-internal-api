package com.labshigh.aicfo.internal.api.board.notice.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.board.notice.model.request.NoticeInsertRequestModel;
import com.labshigh.aicfo.internal.api.board.notice.model.request.NoticeRequestModel;
import com.labshigh.aicfo.internal.api.board.notice.model.request.NoticeUpdateRequestModel;
import com.labshigh.aicfo.internal.api.board.notice.service.NoticeService;
import com.labshigh.aicfo.internal.api.board.notice.validator.NoticeInsertRequestValidator;
import com.labshigh.aicfo.internal.api.board.notice.validator.NoticeListRequestValidator;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/api/board")
public class NoticeController {
    private final NoticeService boardService;

    public NoticeController(NoticeService boardService) {
        this.boardService = boardService;
    }

    @ApiOperation("Member 리스트 조회")
    @GetMapping(value="/notice", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> list(NoticeRequestModel boardRequestModel, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();
        NoticeListRequestValidator.builder().build()
                .validate(boardRequestModel, bindingResult);

        if(bindingResult.hasErrors()) {
            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            try {
                responseModel.setData(boardService.list(boardRequestModel));
            } catch (ServiceException se) {
                responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
                responseModel.setMessage(se.getMessage());
            } catch (Exception e) {
                responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
                responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                responseModel.error.setErrorMessage(e.getLocalizedMessage());
            }
        }
        return responseModel.toResponse();
    }

    @ApiOperation("Member 리스트 조회")
    @GetMapping(value="/listForAdmin", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> listForAdmin(NoticeRequestModel boardRequestModel, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();
        NoticeListRequestValidator.builder().build()
                .validate(boardRequestModel, bindingResult);

        if(bindingResult.hasErrors()) {
            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            try {
                responseModel.setData(boardService.listForAdmin(boardRequestModel));
            } catch (ServiceException se) {
                responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
                responseModel.setMessage(se.getMessage());
            } catch (Exception e) {
                responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
                responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                responseModel.error.setErrorMessage(e.getLocalizedMessage());
            }
        }
        return responseModel.toResponse();
    }

    @ApiOperation("공지사항 상세 조회")
    @GetMapping(value="/notice/detail", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> detail(NoticeRequestModel boardRequestModel, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();
//        BoardListRequestValidator.builder().build()
//                .validate(boardRequestModel, bindingResult);

        if(bindingResult.hasErrors()) {
            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            try {
                responseModel.setData(boardService.detail(boardRequestModel));
            } catch (ServiceException se) {
                responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
                responseModel.setMessage(se.getMessage());
            } catch (Exception e) {
                responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
                responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                responseModel.error.setErrorMessage(e.getLocalizedMessage());
            }
        }
        return responseModel.toResponse();
    }


    @ApiOperation("공지사항 등록")
    @PostMapping(value = "/notice", produces = {Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> insert(@RequestBody NoticeInsertRequestModel boardInsertRequestModel,
                                         BindingResult bindingResult) {

        ResponseModel responseModel = new ResponseModel();
        NoticeInsertRequestValidator.builder().build().validate(boardInsertRequestModel, bindingResult);

        if (bindingResult.hasErrors()) {
            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            try {
                responseModel.setData(boardService.insert(boardInsertRequestModel));
            } catch ( ServiceException se ) {
                responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
                responseModel.setMessage(se.getMessage());
            } catch ( Exception e ) {
                responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
                responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                responseModel.error.setErrorMessage(e.getLocalizedMessage());
            }
        }
        return responseModel.toResponse();
    }


    @ApiOperation("공지사항 수정")
    @PutMapping(value = "/notice", produces = {Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> update(@RequestBody NoticeUpdateRequestModel boardUpdateRequestModel,
                                                BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();

        try {
            responseModel.setData(boardService.update(boardUpdateRequestModel));
        } catch ( ServiceException se ) {
            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            responseModel.setMessage(se.getMessage());
        } catch ( Exception e ) {
            responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseModel.error.setErrorMessage(e.getLocalizedMessage());
        }
        return responseModel.toResponse();
    }

    @ApiOperation("Member 리스트 조회")
    @GetMapping(value="/noticeTypeList", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> noticeTypeList() {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel.setData(boardService.noticeTypeList());
        } catch (ServiceException se) {
            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            responseModel.setMessage(se.getMessage());
        } catch (Exception e) {
            responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseModel.error.setErrorMessage(e.getLocalizedMessage());
        }
        return responseModel.toResponse();
    }

    @ApiOperation("Member 리스트 조회")
    @GetMapping(value="/popupList", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> popupList() {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel.setData(boardService.popupNotice());
        } catch (ServiceException se) {
            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            responseModel.setMessage(se.getMessage());
        } catch (Exception e) {
            responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseModel.error.setErrorMessage(e.getLocalizedMessage());
        }
        return responseModel.toResponse();
    }

}
