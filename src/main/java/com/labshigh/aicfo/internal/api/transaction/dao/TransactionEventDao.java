package com.labshigh.aicfo.internal.api.transaction.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.aicfo.internal.api.common.Constants;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEventDao {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private Boolean usedFlag;
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
  private BigDecimal amount;
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
  private Long memberUid;
}
