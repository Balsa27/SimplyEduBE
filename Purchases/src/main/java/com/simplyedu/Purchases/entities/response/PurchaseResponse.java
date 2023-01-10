package com.simplyedu.Purchases.entities.response;

import com.simplyedu.Purchases.entities.CoursePurchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponse {
    Set<CoursePurchase> coursePurchaseResponse;
}
