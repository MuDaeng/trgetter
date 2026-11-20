package com.mudaeng.trgetter.api;

import com.mudaeng.trgetter.client.mudaeng.MudaengRegistToss;
import com.mudaeng.trgetter.client.mudaeng.MudaengTossTransaction;
import com.mudaeng.trgetter.client.toss.*;
import com.mudaeng.trgetter.configuration.TossConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class TrGetterService {

    /** 만료 직전 요청 실패를 막기 위해 만료 60초 전에 재발급 */
    private static final long EXPIRY_MARGIN_SECONDS = 60;

    /** 잔고에 반영할 거래 유형 (입고: 이벤트·소수점 주식 지급 등, 단가는 Mudaeng 서버가 입고일 종가로 채움) */
    private static final String TRADE_TYPES = "BUY,SELL,SECURITIES_TRANSFER_IN,SECURITIES_TRANSFER_OUT";

    private final TossFeignClient tossFeignClient;

    private Instant tokenExpiresAt = Instant.EPOCH;

    /**
     * 거래내역(취소 제외, 오래된 순)을 Mudaeng /balance/toss 요청 형식으로 반환
     */
    public MudaengRegistToss getTossTransactions(String clientId, String clientSecret) {
        var transactionList = getTransactions(clientId, clientSecret).stream().filter(Predicate.not(Transaction::isCancellation)).toList();
        return MudaengRegistToss.builder()
                .transactionList(transactionList.reversed().stream().map(MudaengTossTransaction::from).toList())
                .build();
    }

    /**
     * 계좌 목록 조회 (토큰이 없거나 만료됐으면 먼저 발급)
     */
    public Accounts getAccounts(String clientId, String clientSecret) {
        ensureAccessToken(clientId, clientSecret);
        return tossFeignClient.getAccounts();
    }

    /**
     * 거래내역 조회 - 계좌 목록의 첫 번째 계좌 seq를 X-Tossinvest-Account 헤더로 사용
     */
    public List<Transaction> getTransactions(String clientId, String clientSecret) {
        List<AccountInfo> accounts = getAccounts(clientId, clientSecret).getResult();
        if (accounts == null || accounts.isEmpty()) {
            throw new IllegalStateException("조회 가능한 계좌가 없습니다.");
        }
        int accountSeq = accounts.getFirst().getAccountSeq();
        Transactions tr = null;
        List<Transaction> transactionList = new ArrayList<>();
        do {
            String cursor = null;
            if (tr != null) cursor = tr.getResult().getNextCursor();
            tr = tossFeignClient.getTransactions(accountSeq, TRADE_TYPES, cursor, 100);
            transactionList.addAll(tr.getResult().getTransactions());
        } while(tr.getResult().isHasNext());
        return transactionList;
    }

    /**
     * 토큰이 없거나 만료됐을 때만 토스 AccessToken을 발급해 TossConfig.key에 저장
     * (토스는 새 토큰을 발급하면 이전 토큰이 무효화되므로 요청마다 발급하지 않음)
     */
    private synchronized void ensureAccessToken(String clientId, String clientSecret) {
        if (TossConfig.key == null || TossConfig.key.isBlank() || Instant.now().isAfter(tokenExpiresAt)) {
            TokenResponse token = tossFeignClient.getToken(TokenRequest.clientCredentials(clientId, clientSecret));
            TossConfig.key = token.getAccessToken();
            tokenExpiresAt = Instant.now().plusSeconds(token.getExpiresIn() - EXPIRY_MARGIN_SECONDS);
        }
    }
}
