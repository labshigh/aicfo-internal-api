package com.labshigh.aicfo.internal.api.abusing.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.abusing.model.request.AbusingRequestModel;
import com.labshigh.aicfo.internal.api.abusing.model.request.AbusingRestRequestModel;
import com.labshigh.aicfo.internal.api.abusing.service.AbusingService;
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
@RequestMapping(value = "/api/abusing")
public class AbusingController {
    private final AbusingService abusingService;

    public AbusingController(AbusingService abusingService) {
        this.abusingService = abusingService;
    }

    @ApiOperation("abusing 리스트 조회")
    @GetMapping(value="", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> list(AbusingRequestModel requestModel,BindingResult bindResult) {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel.setData(abusingService.list(requestModel));
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

//    @ApiOperation("abusing 리스트 조회")
//    @PostMapping(value="/abusing", produces={Constants.RESPONSE_CONTENT_TYPE})
//    public ResponseEntity<String> abusingMemoList(@RequestBody AbusingRequestModel requestModel, BindingResult bindResult) {
//        ResponseModel responseModel = new ResponseModel();
//        try {
//            responseModel.setData(abusingService.abusingMemoList(requestModel));
//        } catch (ServiceException se) {
//            responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
//            responseModel.setMessage(se.getMessage());
//        } catch (Exception e) {
//            responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//            responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            responseModel.error.setErrorMessage(e.getLocalizedMessage());
//        }
//        return responseModel.toResponse();
//    }

    @ApiOperation("abusing 리스트 조회")
    @GetMapping(value="/memo/{uid}", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> abusingMemoList2(@PathVariable(value = "uid") long uid) {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel.setData(abusingService.abusingMemoList(uid));
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

    @ApiOperation("abusing 하려는 멤버 조회")
    @GetMapping(value="/detailMember", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> detailMember(AbusingRequestModel requestModel,BindingResult bindResult) {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel.setData(abusingService.detailMember(requestModel));
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

    @ApiOperation("abusing 상태변경")
    @PostMapping(value="/restAbuser", produces={Constants.RESPONSE_CONTENT_TYPE})
    public ResponseEntity<String> restAbuser(@RequestBody AbusingRestRequestModel abusingRestRequestModel, BindingResult bindResult) {
        ResponseModel responseModel = new ResponseModel();
        try {
//            responseModel.setData(abusingService.rest(abusingRestRequestModel));
            abusingService.rest(abusingRestRequestModel);
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
