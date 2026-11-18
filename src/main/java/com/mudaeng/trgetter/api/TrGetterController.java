package com.mudaeng.trgetter.api;

import com.mudaeng.trgetter.client.Accounts;
import com.mudaeng.trgetter.client.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tr")
@RequiredArgsConstructor
public class TrGetterController {
    private final TrGetterService trGetterService;

    @GetMapping("/toss")
    public ResponseEntity<TossSuccess> transactionGet() {
        TokenResponse token = trGetterService.issueAccessToken();
        return ResponseEntity.ok(new TossSuccess("토큰 발급 완료 (" + token.getTokenType() + ", 만료 " + token.getExpiresIn() + "초)"));
    }
}
