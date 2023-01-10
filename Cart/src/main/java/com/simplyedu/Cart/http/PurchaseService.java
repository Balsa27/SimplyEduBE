package com.simplyedu.Cart.http;

import com.simplyedu.Cart.config.FeignConfig.FeignConfig;
import com.simplyedu.Cart.http.entities.request.PurchaseRequest;
import com.simplyedu.Cart.http.entities.response.PurchaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "PURCHASES", configuration = {
        FeignConfig.class
})
public interface PurchaseService {
    @PostMapping("purchases/purchase")
    PurchaseResponse purchaseCourses(@RequestBody PurchaseRequest request);
}
