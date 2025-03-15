package com.devsu.hackerearth.backend.account.infrastructure.config;

import com.devsu.hackerearth.backend.account.infrastructure.shared.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return new NotFoundException("Client not found with the given ID");
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
