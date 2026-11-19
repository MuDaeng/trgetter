package com.mudaeng.trgetter.client.mudaeng;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MudaengRegistToss {
    private List<MudaengTossTransaction> transactionList;
}
