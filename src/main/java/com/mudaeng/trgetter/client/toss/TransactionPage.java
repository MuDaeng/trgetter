package com.mudaeng.trgetter.client.toss;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionPage {
    private List<Transaction> transactions;
    /** 다음 페이지 커서 (마지막 페이지면 null) */
    private String nextCursor;
    private boolean hasNext;
}
