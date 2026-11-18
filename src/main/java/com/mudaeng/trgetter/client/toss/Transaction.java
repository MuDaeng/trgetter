package com.mudaeng.trgetter.client.toss;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * 거래내역 1건
 * enum 성격 필드(transactionType 등)는 신규 값이 추가돼도 깨지지 않도록 String으로 받음
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    /** BUY, SELL, SECURITIES_TRANSFER_IN/OUT, DEPOSIT, WITHDRAWAL, EXCHANGE_IN/OUT, DIVIDEND, INTEREST, FEE */
    private String transactionType;
    /** KR, US */
    private String marketCountry;
    /** KRW, USD */
    private String currency;
    /** 종목 심볼 (채권·현금은 null) */
    private String symbol;
    /** STOCK, BOND, OPTION (nullable) */
    private String assetClass;
    private String isinCode;
    private BigDecimal quantity;
    private BigDecimal filledPrice;
    private BigDecimal amount;
    private BigDecimal commission;
    private BigDecimal tax;
    private BigDecimal netAmount;
    /** 1 USD 당 KRW (정산된 USD 거래만) */
    private BigDecimal exchangeRate;
    private LocalDate settlementDate;
    private boolean settled;
    /** KST, 미정산이면 null */
    private OffsetDateTime transactionTime;
    @JsonProperty("isCancellation")
    private boolean isCancellation;
}
