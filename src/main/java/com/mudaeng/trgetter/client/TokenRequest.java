package com.mudaeng.trgetter.client;

import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * /oauth2/token 요청 폼 (application/x-www-form-urlencoded)
 * feign-form은 final 필드를 인코딩하지 않으므로 record 대신 일반 클래스로 작성
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {
    @FormProperty("grant_type")
    private String grantType;

    @FormProperty("client_id")
    private String clientId;

    @FormProperty("client_secret")
    private String clientSecret;

    public static TokenRequest clientCredentials(String clientId, String clientSecret) {
        return new TokenRequest("client_credentials", clientId, clientSecret);
    }
}
