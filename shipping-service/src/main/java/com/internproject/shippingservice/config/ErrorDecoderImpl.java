package com.internproject.shippingservice.config;

import com.internproject.shippingservice.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class ErrorDecoderImpl implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        if (responseStatus.is4xxClientError()) {
            return new NotFoundException("Not found");
        }
        return new Exception();
    }
}
