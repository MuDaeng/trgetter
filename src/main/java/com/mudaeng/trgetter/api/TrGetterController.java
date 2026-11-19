package com.mudaeng.trgetter.api;

import com.mudaeng.trgetter.client.mudaeng.MudaengRegistToss;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tr")
@RequiredArgsConstructor
public class TrGetterController {
    private final TrGetterService trGetterService;

    @GetMapping("/toss")
    public ResponseEntity<MudaengRegistToss> transactionGet(@RequestHeader String clientId, @RequestHeader String clientSecret) {
        return ResponseEntity.ok(trGetterService.getTossTransactions(clientId, clientSecret));
    }
}
