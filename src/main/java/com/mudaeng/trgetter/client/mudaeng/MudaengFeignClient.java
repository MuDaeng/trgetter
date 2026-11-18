package com.mudaeng.trgetter.client.mudaeng;

import com.mudaeng.trgetter.configuration.MudaengConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="mudaeng", url = "https://www.mudaeng.online", configuration = MudaengConfig.class)
public interface MudaengFeignClient {
    @PostMapping("/balance/toss")
    MudaengRegistResponse registBalance(@RequestBody MudaengRegistToss request);
}
