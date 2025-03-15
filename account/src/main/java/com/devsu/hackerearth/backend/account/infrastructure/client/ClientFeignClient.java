package com.devsu.hackerearth.backend.account.infrastructure.client;

import com.devsu.hackerearth.backend.account.application.dto.ClientDto;

import com.devsu.hackerearth.backend.account.infrastructure.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "api-client",
        url = "http://localhost:8001/api/v1/clients",
        configuration = FeignClientConfig.class
)
public interface ClientFeignClient {

    @GetMapping(value = "/{clientCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto getClientByClientCode(@PathVariable String clientCode);

    @GetMapping
    List<ClientDto> getAllClients();
//    ResponseEntity<List<ClientDto>> getAllClients();

}
