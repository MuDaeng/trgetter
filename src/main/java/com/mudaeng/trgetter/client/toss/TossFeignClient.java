package com.mudaeng.trgetter.client.toss;

import com.mudaeng.trgetter.configuration.TossConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="toss", url = "https://openapi.tossinvest.com", configuration= TossConfig.class)
public interface TossFeignClient {
    @GetMapping("/api/v1/accounts")
    Accounts getAccounts();

    /**
     * 거래내역 조회
     * @param accountSeq 계좌 목록 조회로 받은 accountSeq
     */
    @GetMapping("/api/v1/transactions")
    Transactions getTransactions(@RequestHeader("X-Tossinvest-Account") long accountSeq, @RequestParam String transactionTypes, @RequestParam String cursor, @RequestParam int limit);

    /**
     * 액세스 토큰 발급 (client_credentials)
     */
    @PostMapping(value = "/oauth2/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenResponse getToken(@RequestBody TokenRequest request);

}
