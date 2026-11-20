package com.mudaeng.trgetter.client.mudaeng;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mudaeng.trgetter.client.toss.Transaction;
import lombok.*;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

/**
 * Mudaeng 서버(/balance/toss)로 보내는 거래내역 1건
 * 서버 Zod 스키마가 금액·수량을 십진수 문자열("123.45")로 받으므로 BigDecimal을 문자열로 변환해서 보냄
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MudaengTossTransaction {
    private String transactionType;
    private String marketCountry;
    private String currency;
    private String symbol;
    private String assetClass;
    private String isinCode;
    private String quantity;
    private String filledPrice;
    private String amount;
    private String commission;
    private String tax;
    private String netAmount;
    private String exchangeRate;
    /** YYYY-MM-DD */
    private String settlementDate;
    private boolean settled;
    /** ISO 8601 (+09:00), 미정산이면 null */
    private String transactionTime;
    @JsonProperty("isCancellation")
    private boolean isCancellation;

    public static MudaengTossTransaction from(Transaction t) {
        return MudaengTossTransaction.builder()
                .transactionType(t.getTransactionType())
                .marketCountry(t.getMarketCountry())
                .currency(t.getCurrency())
                .symbol(t.getSymbol())
                .assetClass(t.getAssetClass())
                .isinCode(t.getIsinCode())
                .quantity(decimal(t.getQuantity()))
                .filledPrice(decimal(t.getFilledPrice()))
                .amount(decimal(t.getAmount()))
                // 서버에서 필수값 (없으면 "0")
                .commission(decimalOrZero(t.getCommission()))
                .tax(decimalOrZero(t.getTax()))
                .netAmount(decimal(t.getNetAmount()))
                .exchangeRate(decimal(t.getExchangeRate()))
                .settlementDate(t.getSettlementDate() == null ? null : t.getSettlementDate().toString())
                .settled(t.isSettled())
                .transactionTime(t.getTransactionTime() == null ? null
                        : t.getTransactionTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .isCancellation(t.isCancellation())
                .build();
    }

    /** 지수 표기(1E+1)가 나오지 않도록 toPlainString 사용 */
    private static String decimal(BigDecimal value) {
        return value == null ? null : value.toPlainString();
    }

    private static String decimalOrZero(BigDecimal value) {
        return value == null ? "0" : value.toPlainString();
    }
}
