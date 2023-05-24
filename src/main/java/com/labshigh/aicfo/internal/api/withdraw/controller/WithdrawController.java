package com.labshigh.aicfo.internal.api.withdraw.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminSendVerifyEmailRequestModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.member.model.request.MemberSendVerifyEmailRequestModel;
import com.labshigh.aicfo.internal.api.withdraw.model.request.*;
import com.labshigh.aicfo.internal.api.withdraw.service.WithdrawService;
import com.labshigh.aicfo.internal.api.withdraw.validator.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/api/withdraw")
public class WithdrawController {
    private final WithdrawService withdrawService;
    public WithdrawController(WithdrawService withdrawService) {
        this.withdrawService = withdrawService;
    }

    @ApiOperation("출금신청 리스트 조회")
    @GetMapping(value="applyList", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> applyList(WithdrawListRequestModel requestModel, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel.setData(withdrawService.applyList(requestModel));
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

    @ApiOperation("거절유형 리스트 조회")
    @GetMapping(value="rejectTypeList", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> rejectTypeList() {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel.setData(withdrawService.rejectTypeList());
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

    @ApiOperation("출금신청 승인 리스트 조회")
    @GetMapping(value="/completeList", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> completeList(WithdrawListRequestModel requestModel, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel.setData(withdrawService.completeList(requestModel));
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

    @ApiOperation("출금신청 거절 리스트 조회")
    @GetMapping(value="/rejectList", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> rejectList(WithdrawListRequestModel requestModel, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel.setData(withdrawService.rejectList(requestModel));
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

    @ApiOperation("출금신청 관리자 승인/거절 처리")
    @PostMapping(value="rest", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> applyConfirmInsert(@RequestBody WithdrawConfirmInsertRequestModel model, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();
        WithdrawalAdminInsertRequestValidator.builder().build().validate(model, bindingResult);

        if (bindingResult.hasErrors()) {
            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            try {
                responseModel.setData(withdrawService.applyConfirmInsert(model));
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
    @ApiOperation("출금신청 추가 승인/거절 처리")
    @PostMapping(value="/addRest", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> applyAddConfirmInsert(@RequestBody WithdrawConfirmInsertRequestModel model, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();

        WithdrawalAdminInsertRequestValidator.builder().build().validate(model, bindingResult);

        if (bindingResult.hasErrors()) {
            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            try {
                responseModel.setData(withdrawService.applyAddConfirmRequest(model));
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

    @ApiOperation("프론트 출금신청 처리 API")
    @PostMapping(value="/request", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> withdrawalRequest(@RequestBody WithdrawInsertRequestModel model, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();

        WithdrawalInsertRequestValidator.builder().build().validate(model, bindingResult);

        if(bindingResult.hasErrors()) {
            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            try {
                responseModel.setData(withdrawService.insertWithdrawalRequest(model));
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

    @ApiOperation("프론트 출금신청 이력 삭제 처리 API")
    @PutMapping(value="/request/{uid}", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> withdrawalRequestDelete(@PathVariable(value = "uid") long uid) {
        ResponseModel responseModel = new ResponseModel();

        try {
            responseModel.setData(withdrawService.updateWithdrawalRequestHistory(uid));
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

    @ApiOperation(value = "인증메일 발송")
    @PostMapping(value = "/send-mail", produces = {Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> sendVerifyEmail( @RequestBody MemberSendVerifyEmailRequestModel memberSendVerifyEmailRequestModel,
                                                  BindingResult bindingResult) {

        ResponseModel responseModel = new ResponseModel();

        WithdrawalSendVerifyEmailRequestValidator.builder().build()
          .validate(memberSendVerifyEmailRequestModel, bindingResult);

        if (bindingResult.hasErrors()) {
            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {

            try {

                responseModel = withdrawService.sendVerifyEmail(memberSendVerifyEmailRequestModel, Constants.MailType.WITHDRAW,null);
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

    @ApiOperation(value = "인증메일 발송")
    @PostMapping(value = "/admin/send-mail", produces = {Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> sendAdminVerifyEmail( @RequestBody AdminSendVerifyEmailRequestModel adminSendVerifyEmailRequestModel) {

        ResponseModel responseModel = new ResponseModel();

        try {

            responseModel = withdrawService.sendAdminVerifyEmail(adminSendVerifyEmailRequestModel, Constants.MailType.WITHDRAW_ADMIN,null);
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

    @ApiOperation(value = "메일 검증")
    @PostMapping(value = "/mail/veriyfy", produces = {Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> verifyAdminEmailCode( @RequestBody WithdrawVerifyEmailRequestModel requestModel,
                                                   BindingResult bindingResult) {

        ResponseModel responseModel = new ResponseModel();

        WithdrawalVerifyEmailRequestValidator.builder().build()
          .validate(requestModel, bindingResult);

        if (bindingResult.hasErrors()) {
            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            try {
                withdrawService.verifyEmailCode(requestModel);
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

    @ApiOperation(value = "메일 검증")
    @PostMapping(value = "/mail/admin/veriyfy", produces = {Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> verifyEmailCode( @RequestBody WithdrawVerifyEmailRequestModel requestModel,
                                                  BindingResult bindingResult) {

        ResponseModel responseModel = new ResponseModel();

        WithdrawalVerifyEmailRequestValidator.builder().build()
          .validate(requestModel, bindingResult);

        if (bindingResult.hasErrors()) {
            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            try {
                withdrawService.verifyAdminEmailCode(requestModel);
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
