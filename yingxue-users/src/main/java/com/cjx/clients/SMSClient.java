package com.cjx.clients;

import com.cjx.vo.MsgVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("api-sms")
public interface SMSClient {
    @PostMapping("/captchas")
    public void sendMsg(@RequestBody MsgVo msgVO);
}
