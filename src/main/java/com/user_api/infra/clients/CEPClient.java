package com.user_api.infra.clients;

import com.user_api.DTOs.ResponseAddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CEPClient", url = "https://api.postmon.com.br/v1/cep/")
public interface CEPClient {

    @GetMapping("/{CEP}")
    ResponseAddressDTO getAddress(@PathVariable String CEP);
}
