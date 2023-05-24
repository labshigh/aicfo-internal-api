package com.labshigh.aicfo.internal.api.wallet.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.member.model.request.MemberWithdrawalWalletSearchRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.request.MemberWalletAdminRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.request.MemberWalletRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.request.MemberWithdrawalWalletRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.request.WalletTransactionPutRequestModel;
import com.labshigh.aicfo.internal.api.wallet.model.request.WalletTransactionWaitRequestModel;
import com.labshigh.aicfo.internal.api.wallet.service.MemberWalletService;
import com.labshigh.aicfo.internal.api.wallet.validator.MemberWalletAdminRequestValidator;
import com.labshigh.aicfo.internal.api.wallet.validator.MemberWalletRequestValidator;
import com.labshigh.aicfo.internal.api.wallet.validator.MemberWalletWithdrawalDeleteRequestValidator;
import com.labshigh.aicfo.internal.api.wallet.validator.MemberWalletWithdrawalRequestValidator;
import io.swagger.annotations.ApiOperation;
import javax.naming.AuthenticationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/api/member")
public class MemberWalletController {

  private final MemberWalletService memberWalletService;

  public MemberWalletController(MemberWalletService memberWalletService) {
    this.memberWalletService = memberWalletService;
  }

  @ApiOperation(value = "사용자 월렛 발급")
  @PostMapping(value = "/wallet", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertMemberWallet(
      @RequestBody MemberWalletRequestModel memberWalletRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    MemberWalletRequestValidator.builder().build().validate(
        memberWalletRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberWalletService.insertMemberWallet(memberWalletRequestModel));
      } catch (AuthenticationException e) {
        responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseModel.setMessage(e.getMessage());
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

  @ApiOperation(value = "월렛 조회")
  @GetMapping(value = "/{memberUid}/wallet/{walletId}", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> getMemberWallet(
      @PathVariable(name = "memberUid") Long memberUid,
      @PathVariable(name = "walletId") Long walletId) {

    ResponseModel responseModel = new ResponseModel();

    try {
      responseModel.setData(memberWalletService.getMemberWallet(memberUid, walletId));
    } catch (AuthenticationException e) {
      responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
      responseModel.setMessage(e.getMessage());
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

  @ApiOperation(value = "월렛 목록 조회")
  @GetMapping(value = "/{memberUid}/wallet", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> getMemberWalletList(
      @PathVariable(name = "memberUid") Long memberUid
      , MemberWithdrawalWalletSearchRequestModel requestModel) {

    ResponseModel responseModel = new ResponseModel();

    try {
      responseModel.setData(memberWalletService.getMemberWalletList(memberUid));
    } catch (AuthenticationException e) {
      responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
      responseModel.setMessage(e.getMessage());
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

  @ApiOperation(value = "사용자 출금 주소 등록")
  @PostMapping(value = "/wallet/withdrawal/address", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertMemberWithdrawalWallet(
      @RequestBody MemberWithdrawalWalletRequestModel memberWithdrawalWalletRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    MemberWalletWithdrawalRequestValidator.builder().build().validate(
        memberWithdrawalWalletRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(
            memberWalletService.insertMemberWithdrawalWallet(memberWithdrawalWalletRequestModel));
      } catch (AuthenticationException e) {
        responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseModel.setMessage(e.getMessage());
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

  @ApiOperation(value = "출금 주소 목록 조회")
  @GetMapping(value = "/wallet/withdrawal/address", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> getMemberWithdrawalWalletList(
      @RequestBody MemberWithdrawalWalletSearchRequestModel requestModel) {

    ResponseModel responseModel = new ResponseModel();

    try {
      responseModel.setData(memberWalletService.getMemberWithdrawalWalletList(requestModel));
    } catch (AuthenticationException e) {
      responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
      responseModel.setMessage(e.getMessage());
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

  @ApiOperation(value = "내부 지갑 주소 확인")
  @GetMapping(value = "/wallet/withdrawal/address/check", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> getMemberWithdrawalWalletCheck(
      @RequestParam(name = "address") String address) {

    ResponseModel responseModel = new ResponseModel();

    try {
      responseModel.setData(memberWalletService.getMemberWithdrawalWalletCheck(address));
    } catch (AuthenticationException e) {
      responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
      responseModel.setMessage(e.getMessage());
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

  @ApiOperation(value = "사용자 출금 주소 삭제")
  @DeleteMapping(value = "/wallet/withdrawal/address", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> deleteMemberWithdrawalWallet(
      @RequestBody MemberWithdrawalWalletRequestModel memberWithdrawalWalletRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    MemberWalletWithdrawalDeleteRequestValidator.builder().build().validate(
        memberWithdrawalWalletRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        memberWalletService.deleteMemberWithdrawalWallet(memberWithdrawalWalletRequestModel);
      } catch (AuthenticationException e) {
        responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseModel.setMessage(e.getMessage());
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

  @ApiOperation(value = "토큰 목록 조회")
  @GetMapping(value = "/wallet/tokens", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> getTokens() {

    ResponseModel responseModel = new ResponseModel();

    try {
      responseModel.setData(memberWalletService.getTokens());
    } catch (AuthenticationException e) {
      responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
      responseModel.setMessage(e.getMessage());
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

  @ApiOperation(value = "사용자 토큰 목록 조회")
  @GetMapping(value = "/{memberId}/tokens", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> getMyTokens(@PathVariable(value = "memberId") String memberId) {

    ResponseModel responseModel = new ResponseModel();

    try {
      responseModel.setData(memberWalletService.getMyTokens(memberId));
    } catch (AuthenticationException e) {
      responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
      responseModel.setMessage(e.getMessage());
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

  @ApiOperation(value = "송금 대기 요청")
  @PostMapping(value = "/wallet/tokens/put", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> postTransactionsPut(
      @RequestBody WalletTransactionPutRequestModel walletTransactionPutRequestModel) {

    ResponseModel responseModel = new ResponseModel();

    try {
      responseModel.setData(
          memberWalletService.postTransactionsPut(walletTransactionPutRequestModel));
    } catch (AuthenticationException e) {
      responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
      responseModel.setMessage(e.getMessage());
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

  @ApiOperation(value = "송금 대기 요청")
  @PostMapping(value = "/wallet/tokens/transactions/wait", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> postTransactionsWait(
      @RequestBody WalletTransactionWaitRequestModel walletTransactionWaitRequestModel) {

    ResponseModel responseModel = new ResponseModel();

    try {
      responseModel.setData(
          memberWalletService.postTransactionsWait(walletTransactionWaitRequestModel));
    } catch (AuthenticationException e) {
      responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
      responseModel.setMessage(e.getMessage());
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

  @ApiOperation(value = "송금 대기 취소")
  @PostMapping(value = "/wallet/tokens/transactions/:transactionId/cancel", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> postTransactionsCancel(@PathVariable long transactionId) {

    ResponseModel responseModel = new ResponseModel();

    try {
      responseModel.setData(memberWalletService.postTransactionsCancel(transactionId));
    } catch (AuthenticationException e) {
      responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
      responseModel.setMessage(e.getMessage());
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

  @ApiOperation(value = "송금 대기 승인")
  @PostMapping(value = "/wallet/tokens/transactions/:transactionId/confirm", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> postTransactionsConfirm(@PathVariable long transactionId) {

    ResponseModel responseModel = new ResponseModel();

    try {
      responseModel.setData(memberWalletService.postTransactionsConfirm(transactionId));
    } catch (AuthenticationException e) {
      responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
      responseModel.setMessage(e.getMessage());
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

  @ApiOperation(value = "memberUid 로 member 가져오기")
  @GetMapping(value = "/{memberUid}/wallet/{walletId}")
  public ResponseEntity<String> getMemberWallet(@PathVariable(value = "memberUid") long memberUid,
      @PathVariable(value = "walletId") long walletId
  ) {

    ResponseModel responseModel = new ResponseModel();

    if (memberUid <= 0 || walletId <= 0) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uid"));
    } else {
      try {
        responseModel.setData(memberWalletService.getMemberWallet(memberUid, walletId));
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

  @ApiOperation(value = "관리자에서 월렛 정보 조회")
  @GetMapping(value = "/wallet/search", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> getSearchMemberWallet(
      MemberWalletAdminRequestModel requestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();
    MemberWalletAdminRequestValidator.builder().build().validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberWalletService.getAdminMemberWallet(requestModel));
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

  @ApiOperation(value = "관리자에서 총 balance 조회")
  @GetMapping(value = "/wallet/totalBalance", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> getAdminMemberWalletTotalBalance() {
    ResponseModel responseModel = new ResponseModel();

    try {
      responseModel.setData(memberWalletService.getAdminMemberWalletTotalBalance());
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
