package com.labshigh.aicfo.internal.api.marketItem.controller;

import com.labshigh.aicfo.core.models.ResponseModel;
import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.common.exceptions.ServiceException;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminBuySettlementListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminDetailListByMemberRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminDetailListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminStatisticsRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyAdminWithdrawalRequestRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyCurPriceInfoRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyInsertRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuyListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuySettlementDeleteWithdrawalRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuySettlementInsertRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.ItemBuySettlementUpdateWithdrawalApprovalFlagRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemAdminVipTokenStakingRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemByReferrerRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemListRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemMyReferrerRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.request.MarketItemUpdateRequestModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.ItemBuyInfoResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.model.response.ItemBuyListResponseModel;
import com.labshigh.aicfo.internal.api.marketItem.service.MarketItemService;
import com.labshigh.aicfo.internal.api.marketItem.validator.ItemBuyAdminBuySettlementListRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.ItemBuyAdminDetailListByMemberRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.ItemBuyAdminDetailListRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.ItemBuyAdminListRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.ItemBuyAdminStatisticsRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.ItemBuyAdminWithdrawalRequestRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.ItemBuyCurPriceInfoRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.ItemBuyInsertRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.ItemBuyListRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.ItemBuySettlementDeleteWithdrawalRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.ItemBuySettlementInsertRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.ItemBuySettlementUpdateWithdrawalApprovalRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.MarketItemAdminVipTokenStakingRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.MarketItemByReferrerRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.MarketItemListRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.MarketItemMyReferrerRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.MarketItemRequestListRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.MarketItemUpdateRequestValidator;
import com.labshigh.aicfo.internal.api.marketItem.validator.ProfitRequestValidator;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
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
@RequestMapping("/api/marketItem")
public class MarketItemController {

  @Autowired
  private MarketItemService marketItemService;

  @ApiOperation("MarketItem 구매")
  @PostMapping(value = "/{itemUid}/buy", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertItemBuy(
      @RequestBody ItemBuyInsertRequestModel itemBuyInsertRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    //itemBuyInsertRequestModel.setItemKind(2);

    ItemBuyInsertRequestValidator.builder().build()
        .validate(itemBuyInsertRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.insertItemBuy(itemBuyInsertRequestModel));
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

  @ApiOperation("MarketItem 수정")
  @PutMapping(value = "/{itemUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updateItem(
      @RequestBody MarketItemUpdateRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MarketItemUpdateRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        marketItemService.updateItem(requestModel);
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


  @ApiOperation("스테이킹 정산 요청")
  @PostMapping(value = "/{itemUid}/settlement", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertItemBuySettlement(
      @RequestBody ItemBuySettlementInsertRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    ItemBuySettlementInsertRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.insertItemBuySettlement(requestModel));
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

  @ApiOperation("Market Item 조회")
  @GetMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listItem(
      MarketItemListRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MarketItemListRequestValidator.builder().build()
        .validate(requestModel, bindingResult);
    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.list(requestModel));
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

  @ApiOperation("Market Item 진행 리스트 조회")
  @GetMapping(value = "/request", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listRequestItem(
      MarketItemListRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MarketItemRequestListRequestValidator.builder().build()
        .validate(requestModel, bindingResult);
    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        if (requestModel.getProcessStatus().equals("1")) {
          responseModel.setData(marketItemService.listRequestItem(requestModel));
        } else {
          responseModel.setData(marketItemService.listRequestItemResponseList(requestModel));
        }

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

  @ApiOperation("추천인 Item 리스트 조회")
  @GetMapping(value = "/byReferrer", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listRequestItemByReferrer(
      MarketItemByReferrerRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MarketItemByReferrerRequestValidator.builder().build()
        .validate(requestModel, bindingResult);
    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        if (requestModel.getProcessStatus().equals("1")) {
          responseModel.setData(marketItemService.listRequestItemByReferrer(requestModel));
        } else {
          responseModel.setData(
              marketItemService.listRequestItemByReferrerResponseList(requestModel));
        }

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

  @ApiOperation("추천인 리스트 조회")
  @GetMapping(value = "/myReferrer", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listRequestItemMyReferrer(
      MarketItemMyReferrerRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MarketItemMyReferrerRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.listRequestItemMyReferrer(requestModel));
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

  @ApiOperation("스테이킹 추천인 리스트 조회")
  @GetMapping(value = "/staking/myReferrer", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listStakingItemMyReferrer(
      MarketItemMyReferrerRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MarketItemMyReferrerRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.listStakingItemMyReferrer(requestModel));
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

  @ApiOperation("관리자 구매 리스트 조회")
  @GetMapping(value = "/buy/admin", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listAdminBuyItem(
      ItemBuyAdminListRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    ItemBuyAdminListRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.listAdminBuyItem(requestModel));
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

  @ApiOperation("관리자 구매 리스트 조회")
  @GetMapping(value = "/buy/admin/statistics", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listAdminBuyItemStatistics(
      ItemBuyAdminStatisticsRequestModel requestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    ItemBuyAdminStatisticsRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.listAdminBuyItemStatistics(requestModel));
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

  @ApiOperation("관리자 구매 리스트 아이템 기준 조회")
  @GetMapping(value = "/buy/admin/{itemUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> detailListAdminBuyItem(
      ItemBuyAdminDetailListRequestModel requestModel,
      @PathVariable("itemUid") long itemUid,
      BindingResult bindingResult) {

    requestModel.setItemUid(itemUid);
    ResponseModel responseModel = new ResponseModel();

    ItemBuyAdminDetailListRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.detailListAdminBuyItem(requestModel));
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

  @ApiOperation("관리자 구매 리스트 아이템/사용자 기준 조회")
  @GetMapping(value = "/buy/admin/{itemUid}/{memberUid}", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> detailListAdminBuyItemByMember(
      ItemBuyAdminDetailListByMemberRequestModel requestModel,
      @PathVariable("itemUid") long itemUid,
      @PathVariable("memberUid") long memberUid,
      BindingResult bindingResult) {

    requestModel.setItemUid(itemUid);
    requestModel.setMemberUid(memberUid);

    ResponseModel responseModel = new ResponseModel();

    ItemBuyAdminDetailListByMemberRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.detailListAdminBuyItemByMember(requestModel));
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

  @ApiOperation("아이템 자동 이용자 리스 조회")
  @GetMapping(value = "/buy/admin/{itemUid}/autoProc", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> autoProcListAdminBuyItem(
      ItemBuyAdminDetailListByMemberRequestModel requestModel,
      @PathVariable("itemUid") long itemUid,
      BindingResult bindingResult) {

    requestModel.setItemUid(itemUid);

    ResponseModel responseModel = new ResponseModel();

    ItemBuyAdminDetailListByMemberRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.autoProcListAdminBuyItem(requestModel));
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
//adminBuySettlementList

  @ApiOperation("아이템 자동 이용자 리스 조회")
  @GetMapping(value = "/buy/admin/{itemUid}/settlement", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> adminBuySettlementList(
      ItemBuyAdminBuySettlementListRequestModel requestModel,
      @PathVariable("itemUid") long itemUid,
      BindingResult bindingResult) {

    requestModel.setItemUid(itemUid);

    ResponseModel responseModel = new ResponseModel();

    ItemBuyAdminBuySettlementListRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.adminBuySettlementList(requestModel));
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

  @ApiOperation("Item Buy 리스트 조회")
  @GetMapping(value = "/{memberUid}/buy", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> itemBuyList(ItemBuyListRequestModel itemBuyListRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    ItemBuyListRequestValidator.builder().build()
        .validate(itemBuyListRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {

        List<ItemBuyListResponseModel> list = marketItemService.listItemBuy(
            itemBuyListRequestModel);

        Map<Long, String> totalPrice = marketItemService.listItemBuyGroupByItemUid(list);

        responseModel.setData(
            ItemBuyInfoResponseModel.builder().totalPrice(totalPrice).list(list).build());
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

  @ApiOperation("수익 정보 조회")
  @GetMapping(value = "/buy/profit", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> profitItemBuy(ItemBuyListRequestModel itemBuyListRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    ProfitRequestValidator.builder().build()
        .validate(itemBuyListRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.profitItemBuy(itemBuyListRequestModel));
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


  @ApiOperation("관리자 아이템 해지 신청 리스트 조회")
  @GetMapping(value = "/buy/admin/{itemUid}/withdrawalRequest", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> withdrawalRequestListAdminBuyItem(
      ItemBuyAdminWithdrawalRequestRequestModel requestModel,
      @PathVariable("itemUid") long itemUid,
      BindingResult bindingResult) {

    requestModel.setItemUid(itemUid);
    ResponseModel responseModel = new ResponseModel();

    ItemBuyAdminWithdrawalRequestRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.withdrawalRequestListAdminBuyItem(requestModel));
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

  @ApiOperation("관리자 출금 요청 승인")
  @PutMapping(value = "/buy/admin/withdrawalRequest/{itemBuySettlementUid}", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updateWithdrawalApprovalFlag(
      @RequestBody ItemBuySettlementUpdateWithdrawalApprovalFlagRequestModel requestModel,
      @PathVariable("itemBuySettlementUid") long itemBuySettlementUid,
      BindingResult bindingResult) {

    requestModel.setUid(itemBuySettlementUid);
    ResponseModel responseModel = new ResponseModel();

    ItemBuySettlementUpdateWithdrawalApprovalRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        marketItemService.updateWithdrawalApprovalFlag(requestModel);
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

  @ApiOperation("출금 요청 삭제")
  @DeleteMapping(value = "/buy/withdrawalRequest/{itemBuySettlementUid}/withdrawal", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> deleteItemBuySettlementByWithdrawal(
      @RequestBody ItemBuySettlementDeleteWithdrawalRequestModel requestModel,
      @PathVariable("itemBuySettlementUid") long itemBuySettlementUid,
      BindingResult bindingResult) {

    requestModel.setUid(itemBuySettlementUid);
    ResponseModel responseModel = new ResponseModel();

    ItemBuySettlementDeleteWithdrawalRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        marketItemService.deleteItemBuySettlementByWithdrawal(requestModel);
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

  //selectItemBuyCurPriceInfo
  @ApiOperation("내 스테이킹 조회")
  @GetMapping(value = "/buy/{memberUid}/total", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> selectItemBuyCurPriceInfo(
      ItemBuyCurPriceInfoRequestModel requestModel,
      @PathVariable("memberUid") long memberUid,
      BindingResult bindingResult) {

    requestModel.setMemberUid(memberUid);
    ResponseModel responseModel = new ResponseModel();

    ItemBuyCurPriceInfoRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.selectItemBuyCurPriceInfo(requestModel));
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

  @ApiOperation("Market Item 진행 리스트 조회")
  @GetMapping(value = "/listAdminVipTokenStaking", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listAdminVipTokenStaking(
      MarketItemAdminVipTokenStakingRequestModel requestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    MarketItemAdminVipTokenStakingRequestValidator.builder().build()
        .validate(requestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.listAdminVipTokenStaking(requestModel));
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
