package com.labshigh.aicfo.internal.api.admin.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminDetailByAdminIdRequestModel;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminInsertRequestModel;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminListRequestModel;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminLoginHistoryModel;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminSigninRequestModel;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminUpdatePasswordRequestModel;
import com.labshigh.aicfo.internal.api.admin.model.request.AdminUpdateRequestModel;
import com.labshigh.aicfo.internal.api.admin.service.AdminService;
import com.labshigh.aicfo.internal.api.admin.validator.AdminDetailByAdminIdRequestValidator;
import com.labshigh.aicfo.internal.api.admin.validator.AdminInsertRequestValidator;
import com.labshigh.aicfo.internal.api.admin.validator.AdminListRequestValidator;
import com.labshigh.aicfo.internal.api.admin.validator.AdminSigninRequestValidator;
import com.labshigh.aicfo.internal.api.admin.validator.AdminUpdateLoginFailCountRequestValidator;
import com.labshigh.aicfo.internal.api.admin.validator.AdminUpdatePasswordRequestValidator;
import com.labshigh.aicfo.internal.api.admin.validator.AdminUpdateRequestValidator;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
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
@RequestMapping(value = "/api/admin")
public class AdminController {

  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @ApiOperation(value = "admin 등록")
  @PostMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insert(@RequestBody AdminInsertRequestModel adminInsertRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    AdminInsertRequestValidator.builder().build().validate(adminInsertRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        adminService.insert(adminInsertRequestModel);
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

  @ApiOperation(value = "admin signin")
  @PostMapping(value = "/signin", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> signin(@RequestBody AdminSigninRequestModel adminSigninRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    AdminSigninRequestValidator.builder().build().validate(adminSigninRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(adminService.signin(adminSigninRequestModel));
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

  @ApiOperation(value = "admin 수정")
  @PutMapping(value = "/{adminUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> update(@PathVariable(value = "adminUid") long adminUid,
      @RequestBody AdminUpdateRequestModel adminUpdateRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    adminUpdateRequestModel.setAdminUid(adminUid);

    AdminUpdateRequestValidator.builder().build().validate(adminUpdateRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        adminService.update(adminUpdateRequestModel);
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

  @ApiOperation(value = "admin password 수정")
  @PutMapping(value = "/{adminUid}/password", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> update(@PathVariable(value = "adminUid") long adminUid,
      @RequestBody AdminUpdatePasswordRequestModel adminUpdatePasswordRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    adminUpdatePasswordRequestModel.setAdminUid(adminUid);

    AdminUpdatePasswordRequestValidator.builder().build()
        .validate(adminUpdatePasswordRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        adminService.updatePassword(adminUpdatePasswordRequestModel);
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

  @ApiOperation(value = "admin 삭제")
  @DeleteMapping(value = "/{adminUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> delete(@PathVariable(value = "adminUid") long adminUid) {

    ResponseModel responseModel = new ResponseModel();

    if (adminUid <= 0) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "adminUid"));
    } else {
      try {
        adminService.delete(adminUid);
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

  @ApiOperation(value = "admin 리스트")
  @GetMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> list(
      AdminListRequestModel adminListRequestModel, BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    AdminListRequestValidator.builder().build().validate(
        adminListRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(adminService.list(adminListRequestModel));
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

  @ApiOperation(value = "admin 상세보기")
  @GetMapping(value = "/{adminUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> detail(@PathVariable(value = "adminUid") long adminUid) {

    ResponseModel responseModel = new ResponseModel();

    if (adminUid <= 0) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "adminUid"));
    } else {
      try {
        responseModel.setData(adminService.detail(adminUid));
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

  @ApiOperation(value = "admin 상세보기")
  @GetMapping(value = "/byAdminId", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> detailByAdminId(
      AdminDetailByAdminIdRequestModel adminDetailByAdminIdRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    AdminDetailByAdminIdRequestValidator.builder().build().validate(
        adminDetailByAdminIdRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(
            adminService.detailByAdminId(adminDetailByAdminIdRequestModel.getAdminId()));
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

  @ApiOperation(value = "admin 수정")
  @PutMapping(value = "/{adminUid}/loginFailCount", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updateLoginFailCount(
      @PathVariable(value = "adminUid") long adminUid,
      @RequestBody AdminUpdateRequestModel adminUpdateRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    adminUpdateRequestModel.setAdminUid(adminUid);

    AdminUpdateLoginFailCountRequestValidator.builder().build()
        .validate(adminUpdateRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        adminService.updateLoginFailCount(adminUpdateRequestModel);
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

  @ApiOperation(value = "admin 로그인 이력")
  @PostMapping(value = "/history", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertAdminLoginHistory(
      @RequestBody AdminLoginHistoryModel adminLoginHistoryModel) {

    ResponseModel responseModel = new ResponseModel();
    try {
      adminService.insertAdminLoginHistory(adminLoginHistoryModel);
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
