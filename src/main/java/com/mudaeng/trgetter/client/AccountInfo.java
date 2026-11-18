package com.mudaeng.trgetter.client;

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
