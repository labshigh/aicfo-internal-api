package com.labshigh.aicfo.internal.api.mainWallet.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.mainWallet.model.request.MainWalletListRequestModel;
import com.labshigh.aicfo.internal.api.mainWallet.model.request.MainWalletRequestModel;
import com.labshigh.aicfo.internal.api.mainWallet.service.MainWalletService;
import com.labshigh.aicfo.internal.api.mainWallet.validator.MainWalletWithdrawalRequestValidator;
import com.labshigh.aicfo.internal.api.wallet.service.MemberWalletService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/api/mainWallet")
public class MainWalletController {

    private final MainWalletService mainWalletService;
    private final MemberWalletService memberWalletService;

    public MainWalletController(MainWalletService mainWalletService, MemberWalletService memberWalletService) {
        this.mainWalletService = mainWalletService;
        this.memberWalletService = memberWalletService;
    }

    @ApiOperation("메인 지갑 출금 목록")
    @GetMapping(value="/list", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> list(MainWalletListRequestModel model) {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel.setData(mainWalletService.list(model));
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

    @ApiOperation("메인지갑 주소 출금 요청")
    @PostMapping(value = "/withdrawal", produces = {Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> request(@RequestBody MainWalletRequestModel model, BindingResult bindingResult) {
        ResponseModel responseModel = new ResponseModel();

        MainWalletWithdrawalRequestValidator.builder().build().validate(model, bindingResult);

        if(bindingResult.hasErrors()) {
            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            try {
                responseModel.setData(mainWalletService.request(model));
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

    @ApiOperation("메인지갑주소 조회")
    @PostMapping(value="/mainWalletInfo", produces = {Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> getMainWalletAddress() {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel.setData(memberWalletService.getMasterBalance());
        } catch (Exception e) {
            responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseModel.error.setErrorMessage(e.getLocalizedMessage());
        }
        return responseModel.toResponse();
    }

    @ApiOperation("출금대기중 이더조회")
    @PostMapping(value="/pendingAmount", produces = {Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> pendingAmount() {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel.setData(mainWalletService.pendingAmount());
        } catch (Exception e) {
            responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseModel.error.setErrorMessage(e.getLocalizedMessage());
        }
        return responseModel.toResponse();
    }

}
