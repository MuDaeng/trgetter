package com.mudaeng.trgetter.api;

import com.mudaeng.trgetter.client.*;
import com.mudaeng.trgetter.configuration.TossConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrGetterService {

    /** 만료 직전 요청 실패를 막기 위해 만료 60초 전에 재발급 */
    private static final long EXPIRY_MARGIN_SECONDS = 60;

    private final TossFeignClient tossFeignClient;

    @Value("${api.key.toss.client-id}")
    private String clientId;

    @Value("${api.key.toss.client-secret}")
    private String clientSecret;

    private Instant tokenExpiresAt = Instant.EPOCH;

    /**
     * 토스 AccessToken 발급 후 이후 요청에 사용하도록 TossConfig.key에 저장
     */
    public synchronized TokenResponse issueAccessToken() {
        TokenResponse token = tossFeignClient.getToken(TokenRequest.clientCredentials(clientId, clientSecret));
        TossConfig.key = token.getAccessToken();
        tokenExpiresAt = Instant.now().plusSeconds(token.getExpiresIn() - EXPIRY_MARGIN_SECONDS);
        getTransactions();
        return token;
    }

    /**
     * 계좌 목록 조회 (토큰이 없거나 만료됐으면 먼저 발급)
     */
    public Accounts getAccounts() {
        ensureAccessToken();
        return tossFeignClient.getAccounts();
    }

    /**
     * 거래내역 조회 - 계좌 목록의 첫 번째 계좌 seq를 X-Tossinvest-Account 헤더로 사용
     */
    public void getTransactions() {
        List<AccountInfo> accounts = getAccounts().getResult();
        if (accounts == null || accounts.isEmpty()) {
            throw new IllegalStateException("조회 가능한 계좌가 없습니다.");
        }
        int accountSeq = accounts.get(0).getAccountSeq();
        Transactions tr = null;
        List<Transaction> transactionList = new ArrayList<>();
        do {
            String cursor = null;
            if (tr != null) cursor = tr.getResult().getNextCursor();
            tr = tossFeignClient.getTransactions(accountSeq, "BUY,SELL", cursor, 100);
            transactionList.addAll(tr.getResult().getTransactions());
        } while(tr.getResult().isHasNext());
    }

    private synchronized void ensureAccessToken() {
        if (TossConfig.key == null || TossConfig.key.isBlank() || Instant.now().isAfter(tokenExpiresAt)) {
            issueAccessToken();
        }
    }
}
