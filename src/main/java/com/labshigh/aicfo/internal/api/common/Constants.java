package com.labshigh.aicfo.internal.api.common;

public class Constants {

  // Log Status
  public enum LogStatus {
    PROGRESS, COMPLETE, ERROR;
  }

  public enum MailType {
    JOIN, FIND_PASSWORD, FIND_PASSWORD_IMSI, CUSTOM, WITHDRAW, WITHDRAW_ADMIN;
  }

  // 응답 컨텐츠 타입
  public static final String REQUEST_CONTENT_TYPE = "application/json; charset=UTF-8";
  public static final String RESPONSE_CONTENT_TYPE = "application/json; charset=UTF-8";

  // iso 8601 json 날짜 포멧
  public static final String JSONFY_LOCAL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

  public static final String JSONFY_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
  public static final String ES_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.Z";
  public static final String VIEW_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String UI_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String TIMEZONE = "Asia/Seoul";

  // 기본 페이지 사이즈
  public static final int DEFAULT_PAGE_SIZE = 20;
  // 최대 페이지 사이즈
  public static final int MAX_LIST_PAGE_SIZE = 1000;

  // http status
  public static final String RESPONSE_CODE_CORE_ERROR_MSG = "CORE API Response Error";

  // 이메일 최소/최대 글자수
  public static final int MIN_EMAIL_INPUT_SIZE = 5;
  public static final int MAX_EMAIL_INPUT_SIZE = 256;

  // 비밀번호 최소 글자수
  public static final int MIN_PASSWORD_INPUT_SIZE = 6;

  public static final String MSG_NO_DATA = "There is no data. Please recheck.";
  public static final String MSG_WALLET_ERROR = "지갑 서비스 오류 입니다.";
  public static final String MSG_DUPLICATED_DATA = "There is duplicated data already.";
  public static final String MSG_REQUIRE_FIELD_ERROR = "Please input %s in the required.";

  public static final String MSG_MIN_LENGTH_FIELD_ERROR = "최소 %d자 이상 입력해야합니다.";
  public static final String MSG_MIN_LENGTH_OVER_ERROR = "최소 %d개 이상 입력해야합니다.";
  public static final String MSG_MAX_LENGTH_FIELD_ERROR = "최대 %d자까지 입력 가능합니다.";
  public static final String MSG_MAX_LENGTH_OVER_ERROR = "You can input up to %d.";
  public static final String MSG_PARAMETERS_ERROR = "파라미터가 부족합니다.";

  // Member
  public static final String MSG_WRONG_PASSWORD_ERROR = "Data you entered is incorrect. Please recheck.";
  public static final String MSG_CURRENT_PASSWORD_ERROR = "패스워드 변경시 현재 패스워드가 필요합니다.";
  public static final String MSG_CURRENT_PASSWORD_MATCHING_ERROR = "현재 비밀번호가 일치하지 않습니다.";
  public static final String MSG_PASSWORD_MATCHING_ERROR = "비밀번호와 확인 비밀번호가 다릅니다.";
  public static final String MSG_NOT_MATCH_PASSWORD = "Passwords do not match.";
  public static final String MSG_NOT_MATCH_NAME = "유효한 이름이 아닙니다.";
  public static final String MSG_NOT_MATCH_EMAIL = "Invalid email address. Please recheck.";
  public static final String MSG_PASSWORD_SAME_ERROR = "New password and current password are the same.";
  public static final String MSG_MEMBER_NO = "This email address is unregistered account.";
  public static final String MSG_MEMBER_WALLET_NO = "This sub account is unregistered.";
  public static final String MSG_MEMBER_WALLET_NOT_REQUEST = "There is a withdrawal request in progress.";
  public static final String MSG_MEMBER_REFFERER_NO = "Invalid referral code. Please recheck.";
  public static final String MSG_MEMBER_REFFERER_USER_NO = "You can not input your own referral code.";
  public static final String MSG_MEMBER_ALREADY_REFFERER_NO = "You have already got the referral code registered.";
  public static final String MSG_TOKEN_ERROR = "Invalid token. Please recheck.";
  public static final String MSG_CODE_ERROR = "Invalid code. Please recheck.";
  public static final String MSG_VALUE_ERROR = "Invalid value. Please recheck.";
  public static final String MSG_ALREADY_VERIFIED_EMAIL = "Email verified process is already done.";
  public static final String MSG_NOT_VERIFIED_EMAIL = "Not yet complete the email verification.";
  public static final String MSG_ALREADY_VERIFIED_SMS = "SMS verified process is already done.";
  public static final String MSG_NOT_ACCEPTABLE_FILE = "Not acceptable media file extension";
  public static final String MSG_VERIFY_CONTENT_SMS = "From aicfo: %s is your verification code.\nIf you didn’t request this code, please contact us on Help@fognet.io";
  public static final String MSG_AMDIN_LOGIN_VERIFY_CONTENT_SMS = "[Martini Pool/ Admin] Login verification code is %s";

  public static final String MSG_AMDIN_WITHDRAWAL_VERIFY_CONTENT_SMS = "[Martini Pool/ Admin] Main wallet withdrawal verification code is  %s";

  public static final String MSG_WALLET_INSUFFICIENT_BALANCE = "Available withdrawal quantity is insufficient.";
  public static final String MSG_DUPLICATE_ACCOUNT = "다른 경로로 가입 완료된 이메일 아이디입니다.";

  // 계정 종류 (1:email, 2:metamask)
  public static final int MAX_KIND_INPUT = 2;

  // Community Board
  public static final int MAX_COMMUNITY_TITLE_LENGTH = 500;

  public static final String MSG_COMMUNITY_SEARCH_ERROR = "검색컬럼 또는 검색어를 입력해주세요.";
  public static final String MSG_COMMUNITY_ALREADY_MEMBER = "이미 해당 Group 에 Member 입니다.";
  public static final String MSG_COMMUNITY_ALREADY_INVITE = "이미 해당 Group 에 초대 된 회원입니다.";
  public static final String MSG_COMMUNITY_NO_MEMBER = "해당 Group 에 Member 가 아닙니다.";
  public static final String MSG_COMMUNITY_INVITE_SAME_MEMBER = "본인은 초대 할수가 없습니다.";

  // Market
  public static final int MAX_MARKET_NAME_LENGTH = 100;
  public static final String MSG_MARKET_NOT_MY_MARKET = "본인이 생성한 Market 에만 등록이 가능합니다.";
  public static final String MSG_MARKET_ITEM_MIN_LENGTH_OVER_ERROR = "%s 는 최소 1개 이상 입력해야합니다.";

  public static final String MSG_MARKET_ITEM_UPDATE_START_AT_ERROR = "Start date cannot be earlier than today's date.";

  // MarketItem2
  public static final String MSG_MARKET_ITEM2_EDIT_ERROR = "수정이 불가능한 아이템입니다.";
  public static final String MSG_MARKET_ITEM2_LOCK_ERROR = "잠금되어 있는 아이템은 수정 여부 업데이트가 불가능합니다.";
  public static final String MSG_MARKET_ITEM2_DELETE_ERROR = "삭제가 불가능한 아이템입니다.";
  public static final String MSG_MARKET_ITEM2_LOCK_DELETE_ERROR = "잠금되어 있는 아이템은 삭제가 불가능합니다.";

  public static final String MSG_MARKET_ITEM2_CURRENT_QUANTITY_ERROR = "남은 수량보다 판매하려는 수량이 많을 수 없습니다.";
  public static final String MSG_MARKET_ITEM2_BUY_CURRENT_QUANTITY_ERROR = "판매 수량보다 구매하려는 수량이 많을 수 없습니다.";

  public static final String MSG_MARKET_ITEM2_SELL_QUANTITY_ERROR = "발매 수량이 구매 한 수량보다 많을 수 없습니다.";
  public static final String MSG_MARKET_ITEM2_BUY_MINTING_STATUS_ERROR = "민팅되지 않은 아이템입니다..";
  public static final String MSG_MARKET_ITEM2_MEMBER_ERROR = "본인이 생성 한 아이템만 판매가 가능합니다.";
  public static final String MSG_MARKET_ITEM2_ITEM_KIND_ERROR = "본인이 생성 한 아이템만 수정 가능합니다.";
  public static final String MSG_MARKET_ITEM2_MY_ITEM_BUY_ERROR = "본인이 생성 한 아이템은 구매가 불가능합니다.";
  public static final String MSG_MARKET_ITEM2_END_DATE_BUY_ERROR = "판매가 마감 된 아이템은 구매가 불가능합니다.";
  public static final String MSG_MARKET_ITEM2_REPORT_MEMBER_ERROR = "본인이 판매중인 아이템은 신고 할 수 없습니다.";

  // ShortUrl
  public static final String MSG_COMMUNITY_WRONG_SHORTURL = "잘못된 ShortUrl 입니다.";
  //Scam Board
  public static final String MSG_WRONG_URL = "올바르지 않은 Url 입니다.";
  public static final String MSG_SCAM_BOARD_OPINION_NAME_ERROR = "Opinion 이름이 올바르지 않습니다.";
  public static final String MSG_SCAM_BOARD_OPINION_INCREMENT_ERROR = "증가 값이 잘못 됐습니다.";
  public static final String MSG_SCAM_BOARD_OPTINON_DUPLICATE_ERROR = "이미 체크 하였습니다.";

  // Market Comment
  public static final String MSG_MARKET_COMMENT_NOT_MY_COMMENT = "본인이 작성한 코멘트만 수정, 삭제가 가능합니다.";
  // Item
  public static final String MSG_ITEM_QUANTITY_ERROR = "수량이 0 이하 일수 없습니다.";
  public static final String MSG_ITEM_MEMBER_ERROR = "본인이 생성 한 아이템만 판매가 가능합니다.";
  public static final String MSG_ITEM_BUY_MEMBER_ERROR = "본인이 구매 한 아이템만 판매가 가능합니다.";
  public static final String MSG_ITEM_BUY_CURRENT_QUANTITY_ERROR = "판매 수량보다 구매하려는 수량이 많을 수 없습니다.";

  //Market Item
  public static final String MSG_MARKET_ITEM_BUY_MINTING_STATUS_ERROR = "민팅되지 않은 아이템입니다..";
  public static final String MSG_MARKET_ITEM_MY_ITEM_BUY_ERROR = "본인이 생성 한 아이템은 구매가 불가능합니다.";
  public static final String MSG_MARKET_ITEM_END_DATE_BUY_ERROR = "판매가 마감 된 아이템은 구매가 불가능합니다.";
  public static final String MSG_MARKET_ITEM_ZERO_QUANTITY_SELL_ERROR = "판매 취소 할 아이템이 없습니다.";
  public static final String MSG_MARKET_ITEM_CLOSE_ERROR = "This is a staking has been deposit deadline.";
  public static final String MSG_MARKET_ITEM_CLOSE_WITHDRAWAL_ERROR = "Withdrawal request has been closed.";
  public static final String MSG_MARKET_ITEM_ALREADY_WITHDRAWAL_REQUEST = "Withdrawal has already been requested.";
  public static final String MSG_MARKET_ITEM_TODAY_ERROR = "This is not today's date.";
  public static final String MSG_MARKET_ITEM_WITHDRAWAL_PRICE_OVER = "Withdrawal request limit quantity exceeded.";

  public static final String MSG_MARKET_ITEM_ADMIN_WITHDRAWAL_DELETE_ERROR = "Only the settlement details approved for withdrawal can be canceled.";
  public static final String MSG_MARKET_ITEM_MEMBER_WITHDRAWAL_DELETE_ERROR = "Cancellation is not possible because withdrawal has already been approved.";

  public static final String MSG_MARKET_ITEM_MIN_PRICE_ERROR = "Less than the minimum amount for staking application.";

  //Market Item Log
  public static final String MSG_MARKET_ITEM_BUY_LOG_EMPTY_KEY_ERROR = "There is no log key.";

  // totp
  public static final String MSG_TOTP_ALREADY_USED = "totp already used in. Please recheck.";
  public static final String MSG_TOTP_NOT_USED = "Not yet used the totp.";
  public static final String MSG_TOTP_CODE_LENGTH_FIELD_ERROR = "Please input the proper code in 6 digit.";

  // counsel

  public static final String MSG_COUNSEL_ALREADY_COMPLETE_ERROR = "이미 완료된 상담예약입니다.";
  public static final String MSG_COUNSEL_COMPLETE_CANCEL_ERROR = "상담 완료된 건은 취소 할 수 없습니다.";
  public static final String MSG_COUNSEL_ALREADY_CANCEL_ERROR = "이미 취소된 상담예약입니다.";
  public static final String MSG_COUNSEL_NOW_DATETIME_CANCEL_ERROR = "상담시간이 지난 상담예약은 취소 할 수 없습니다.";

  // inquiry
  public static final String MSG_INQUIRY_ALREADY_UPDATE_INQUIRY_TIME_ERROR = "이미 상담시간이 업데이트 되어 있습니다.";

  private Constants() {
    throw new IllegalStateException("Constants Class");
  }
}
