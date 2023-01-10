package com.simplyedu.Purchases.entities.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class PurchaseRequest {
    Long userId;
    Set<Long> courseIds;
}
