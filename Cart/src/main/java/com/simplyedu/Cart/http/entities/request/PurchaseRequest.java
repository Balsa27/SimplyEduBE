package com.simplyedu.Cart.http.entities.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PurchaseRequest {
    Long userId;
    Set<Long> courseIds;
}
