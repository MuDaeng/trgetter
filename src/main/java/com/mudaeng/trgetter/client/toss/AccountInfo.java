package com.mudaeng.trgetter.client.toss;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AccountInfo {
    private String accountNo;
    private int accountSeq;
    private String accountType;
}
