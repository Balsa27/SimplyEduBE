package com.simplyedu.Statistics.config.FeignConfig;

import com.simplyedu.Statistics.exception.ForbiddenException;
import com.simplyedu.Statistics.exception.ServiceNotAvailableException;
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