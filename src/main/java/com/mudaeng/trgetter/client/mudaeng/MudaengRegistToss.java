package com.mudaeng.trgetter.client.mudaeng;

import com.mudaeng.trgetter.client.toss.Transaction;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MudaengRegistToss {
    private List<Transaction> transactionList;
}
