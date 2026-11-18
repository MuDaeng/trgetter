package com.mudaeng.trgetter.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * GET /api/v1/transactions 응답
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transactions {
    private TransactionPage result;
}
