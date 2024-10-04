package com.user_api.infra.clients;

import com.user_api.DTOs.RequestTelegramMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "TelegramClient", url = "https://api.telegram.org/bot7170603575:AAETNwWqWAgAMZ_V_PqL_Kp0UNLm18vQzBA")
public interface TelegramClient {

    @PostMapping("/sendMessage")
    void sendMessage(@RequestBody RequestTelegramMessageDTO requestTelegramMessageDTO);
}
