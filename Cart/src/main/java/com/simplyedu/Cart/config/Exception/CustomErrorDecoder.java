package com.simplyedu.Cart.config.Exception;

import com.simplyedu.Cart.exceptions.ForbiddenException;
import com.simplyedu.Cart.exceptions.ServiceNotAvailableException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 403:
                return new ForbiddenException("You are not authorized to access this resource");
            case 503:
                return new ServiceNotAvailableException("Other service is not available");
            default:
                return new Exception("An unexpected error ocurred");
        }
    }
}