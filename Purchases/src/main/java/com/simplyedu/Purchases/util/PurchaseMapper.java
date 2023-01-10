package com.simplyedu.Purchases.util;

import com.simplyedu.Purchases.entities.Purchase;
import com.simplyedu.Purchases.entities.response.PurchaseDetailedResponse;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class PurchaseMapper {
    public PurchaseDetailedResponse mapPurchaseToPurchaseDetailedResponse(List<Purchase> allPurchases) {
        return allPurchases == null ? null : PurchaseDetailedResponse.builder()
                .allPurchases(allPurchases)
                .build();
    }
}
