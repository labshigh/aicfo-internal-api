package com.labshigh.aicfo.internal.api.transaction.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEventWalletInsertRequestModel {
  /**
   * 사용자 아이디
   */
  private String userId;
  /**
   * 거래 토큰 아이디
   */
  private Integer tokenId;

  /**
   * 거래 토큰 심볼
   */
  private String tokenSymbol;
  /**
   * 거래 아이디
   */
  private Long transactionId;
  /**
   * 거래 수량
   */
  private Double amount;
  /**
   * 보낸 사람 주소
   */
  private String fromAddress;
  /**
   * 내부 거래시 보낸 사용자 아이디
   */
  private String fromUserId;
  /**
   * 받는 사람 주소
   */
  private String toAddress;
  /**
   * 내부 거래시 받는 사용자 아이디
   */
  private String toUserId;
  /**
   * 거래 타입
   * II: 일반 입금,
   * IO: 일반 출금,
   * EI: 외부 입금,
   * EO: 외부 출금,
   * TF: 수수료 출금
   */
  private String transactionType;
  /**
   * 거래 상태
   */
  private String transactionStatus;
  /**
   * 외부 입출금시 발생한 TX HASH
   */
  private String txHash;


}
