package com.internproject.orderservice.config.feign;

import com.internproject.orderservice.exception.ProductNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class ErrorDecoderImpl implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        if (responseStatus.is4xxClientError()) {
            return new ProductNotFoundException("Can not found product you add to cart");
        }
        return new Exception();
    }
}
